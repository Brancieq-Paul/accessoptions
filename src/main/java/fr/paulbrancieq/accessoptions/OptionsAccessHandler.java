package fr.paulbrancieq.accessoptions;

import fr.paulbrancieq.accessoptions.commons.client.gui.screen.ReloaderConfirmationScreen;
import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.Option;
import fr.paulbrancieq.accessoptions.commons.reloader.AskConfirmation;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.reloader.ReloaderComparator;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import fr.paulbrancieq.accessoptions.commons.storage.StorageSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.*;

/**
 * Class that handles the access to the options. Should have one instance per modification context.
 */
public class OptionsAccessHandler {
  private static final List<StorageSupplier> availableModOptionsStoragesMap = new ArrayList<>();
  private final Map<String, OptionsStorage<?>> modOptionsStoragesMap = new HashMap<>();
  private final List<Option<?, ?>> modifiedOptions = new ArrayList<>();
  private final List<OptionsStorage<?>> modifiedStorages = new ArrayList<>();
  protected final List<Reloader> reloadersFromModifiedOptions = new ArrayList<>();
  protected final List<Reloader> reloadersToRun = new ArrayList<>();
  private Boolean restartNeeded = false;
  private ConfirmationAsker confirmationAsker;
  private Boolean ignoreOptionNotModified = false;
  private Boolean chatFeedback = false;

  /**
   * Constructor for the OptionsAccessHandler.
   */
  @Environment(EnvType.CLIENT)
  public OptionsAccessHandler() {
    for (StorageSupplier storageSupplier : availableModOptionsStoragesMap) {
      storageSupplier.supply(optionsStorage -> {
        modOptionsStoragesMap.put(optionsStorage.getStorageId(), optionsStorage);
        AccessOptions.getLogger().info("Storage \"" + optionsStorage.getStorageId() + "\" registered.");
      }, this);
    }
  }

  /**
   * Registers a mod options storage supplier.
   *
   * @param supplier The supplier to register.
   */
  public static void registerModOptionsStorageSupplier(StorageSupplier supplier) {
    availableModOptionsStoragesMap.add(supplier);
  }

  /**
   * Gets an options storage by its id.
   *
   * @param id The id of the options' storage.
   * @return The options' storage.
   */
  public OptionsStorage<?> getOptionsStorage(String id) {
    return modOptionsStoragesMap.get(id);
  }

  /**
   * Set if the operations should give feedback in the chat.
   *
   * @param chatFeedback If the operations should give feedback in the chat.
   */
  @Environment(EnvType.CLIENT)
  @SuppressWarnings("unused")
  public void setChatFeedback(Boolean chatFeedback) {
    this.chatFeedback = chatFeedback;
  }

  /**
   * Set if the operations should give a feedback when trying to apply a modification with the new value equal to the
   * initial one.
   *
   * @param ignoreOptionNotModified If the operations should give a feedback when trying to apply a modification with
   *                                the new value equal to the initial one.
   */
  @SuppressWarnings("unused")
  public void setIgnoreOptionNotModified(Boolean ignoreOptionNotModified) {
    this.ignoreOptionNotModified = ignoreOptionNotModified;
  }

  /**
   * Sends a feedback message.
   *
   * @param message The message to send.
   */
  @Environment(EnvType.CLIENT)
  private void sendFeedback(String message) {
    AccessOptions.getLogger().info(message);
    if (chatFeedback) {
      MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(message));
    }
  }

  /**
   * Modifies an option. The option will only be modified in the context of this OptionsAccessHandler.
   *
   * @param option The option to modify.
   * @param value  The new value for the option.
   */
  @Environment(EnvType.CLIENT)
  public void modifyOption(Option<?, ?> option, Object value) throws
      AccessOptionsException.OptionTypeMismatch, ValueVerificationException,
      AccessOptionsException.OptionNotModified {
    option.setValue(value);
    modifiedOptions.add(option);
  }

  /**
   * Modifies an option. The option will only be modified in the context of this OptionsAccessHandler.
   *
   * @param modId    The id of the mod that owns the option.
   * @param optionId The id of the option to modify.
   * @param value    The new value for the option.
   */
  @SuppressWarnings("unused")
  @Environment(EnvType.CLIENT)
  public void modifyOption(String modId, String optionId, Object value) throws
      AccessOptionsException.OptionTypeMismatch, ValueVerificationException, AccessOptionsException.OptionNotFound,
      AccessOptionsException.OptionStorageNotFound, AccessOptionsException.OptionNotModified {
    OptionsStorage<?> optionsStorage = getOptionsStorage(modId);
    if (optionsStorage == null) {
      throw new AccessOptionsException.OptionStorageNotFound(modId);
    }
    Option<?, ?> option = optionsStorage.getOption(optionId);
    if (option == null) {
      throw new AccessOptionsException.OptionNotFound(modId, optionId);
    }
    modifyOption(option, value);
  }

  /**
   * Adds a reloader to the list of reloaders that should be run because of a modified option.
   *
   * @param newReloader The reloader to add.
   * @param option      The option the reloader is from.
   */
  @Environment(EnvType.CLIENT)
  private void addOneReloaderFromModifiedOption(Reloader newReloader, Option<?, ?> option) {
    reloadersFromModifiedOptions.stream().filter(newReloader::isSameAs).findFirst().ifPresentOrElse(
        reloaderToRun -> reloaderToRun.addAssociatedModifiedOption(option),
        () -> {
          newReloader.addAssociatedModifiedOption(option);
          reloadersFromModifiedOptions.add(newReloader);
        }
    );
  }

  /**
   * Adds reloaders to the list of reloaders that should be run because of a modified option.
   *
   * @param newReloadersToRun The reloaders to add.
   */
  @Environment(EnvType.CLIENT)
  private void addReloadersFromModifiedOption(Collection<Reloader> newReloadersToRun, Option<?, ?> option) {
    newReloadersToRun.forEach(reloader -> addOneReloaderFromModifiedOption(reloader, option));
  }

  /**
   * Class that handles the confirmation prompts. Should have one instance per prompt.
   */
  @Environment(EnvType.CLIENT)
  private class ConfirmationAsker {
    private final Runnable runAfterAllConfirmations;
    private final List<ReloaderConfirmationScreen> prompts = new ArrayList<>();

    /**
     * Constructor for the ConfirmationAsker.
     *
     * @param runAfterConfirmation The action to run after all confirmations were answered, positive or negative.
     */
    protected ConfirmationAsker(Runnable runAfterConfirmation) {
      this.runAfterAllConfirmations = runAfterConfirmation;
    }

    /**
     * Adds a prompt to the list of prompts.
     *
     * @param reloader The reloader to add a prompt for.
     */
    protected void addPrompt(Reloader reloader) {
      if (!(reloader instanceof AskConfirmation)) {
        AccessOptions.getLogger().warn("Reloader " + reloader.getClass().getName() + " is not an AskConfirmation");
        return;
      }
      ReloaderConfirmationScreen prompt = new ReloaderConfirmationScreen((confirmationResult) -> {
        if (confirmationResult) {
          addOneReloaderToRun(reloader);
        }
        reloader.getAssociatedModifiedOptions().forEach(option ->
            ((AskConfirmation) reloader).getPromptAnswerConsumer(option).accept(confirmationResult));
        runAfterAllConfirmations.run();
        MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(null));
      }, ((AskConfirmation) reloader).getName(), ((AskConfirmation) reloader).getConfirmationText(), reloader);
      if (!prompts.isEmpty()) {
        prompts.get(prompts.size() - 1).setCallback((confirmationResult) -> {
          if (confirmationResult) {
            addOneReloaderToRun(reloader);
          }
          reloader.getAssociatedModifiedOptions().forEach(option ->
              ((AskConfirmation) reloader).getPromptAnswerConsumer(option).accept(confirmationResult)
          );
          MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(prompt));
        });
      }
      prompts.add(prompt);
    }
  }

  /**
   * Adds a reloader to the list of reloaders to run. Called when a prompt is answered positively or when there is no
   * need for a prompt.
   *
   * @param newReloader The reloader to add.
   */
  @Environment(EnvType.CLIENT)
  private void addOneReloaderToRun(Reloader newReloader) {
    if (reloadersToRun.stream().noneMatch(newReloader::isSameAs) &&
        reloadersToRun.stream().noneMatch(newReloader::isChildOf)) {
      reloadersToRun.removeIf(reloader -> reloader.isChildOf(newReloader));
      reloadersToRun.add(newReloader);
    }
  }

  /**
   * Full logic to determine which reloaders can be run and which need a confirmation prompt. To be run before just
   * before prompting the user for confirmation.
   */
  @Environment(EnvType.CLIENT)
  private void setPromptsReloaders() {
    reloadersFromModifiedOptions.clear();
    modifiedOptions.stream().filter((Option::hasChanged)).forEach(option ->
        addReloadersFromModifiedOption(option.getReloaders(), option));
    reloadersFromModifiedOptions.sort(new ReloaderComparator());
    confirmationAsker = new ConfirmationAsker(this::applySaveModifiedOptionsThenRunReloaders);
    reloadersFromModifiedOptions.forEach(reloader -> {
      if (reloader instanceof AskConfirmation) {
        confirmationAsker.addPrompt(reloader);
      } else {
        addOneReloaderToRun(reloader);
      }
    });
  }

  /**
   * Applies the change of the option out of the context of this OptionsAccessHandler. Add the storage to the list of
   * modified storages if it is not already in it so that it can be saved later.
   *
   * @param option The option to apply the change of.
   */
  @Environment(EnvType.CLIENT)
  private void applyModifiedOption(Option<?, ?> option) throws AccessOptionsException.OptionNotModified {
    option.applyChanges();
    option.reset();
    if (!modifiedStorages.contains(option.getStorage())) {
      modifiedStorages.add(option.getStorage());
    }
  }

  /**
   * Restarts the game if needed. Special logic for RequiresGameRestart reloaders.
   */
  @Environment(EnvType.CLIENT)
  private void restartIfNeeded() {
    if (restartNeeded) {
      MinecraftClient.getInstance().scheduleStop();
    }
  }

  /**
   * Runs the reloaders that were set to run.
   */
  @Environment(EnvType.CLIENT)
  private void runReloaders() {
    reloadersToRun.forEach(Reloader::run);
    reloadersToRun.clear();
  }

  /**
   * Applies the changes of the modified options and saves the modified storages, then runs the reloaders.
   */
  @Environment(EnvType.CLIENT)
  private void applySaveModifiedOptionsThenRunReloaders() {
    for (Option<?, ?> option : modifiedOptions) {
      try {
        applyModifiedOption(option);
      } catch (AccessOptionsException.OptionNotModified e) {
        if (!ignoreOptionNotModified) {
          sendFeedback(e.getMessage());
        }
      }
    }
    modifiedStorages.forEach(OptionsStorage::save);
    runReloaders();
    sendFeedback("Options applied and saved.");
    modifiedOptions.clear();
    modifiedStorages.clear();
    restartIfNeeded();
  }

  /**
   * Main method to call to apply the options properly. Prompts the user for confirmation if needed, then applies the
   * changes of the modified options and saves the modified storages, then runs the reloaders.
   */
  @Environment(EnvType.CLIENT)
  public void applyOptions() {
    setPromptsReloaders();
    if (confirmationAsker.prompts.isEmpty()) {
      applySaveModifiedOptionsThenRunReloaders();
    } else {
      MinecraftClient.getInstance().execute(() ->
          MinecraftClient.getInstance().setScreen(confirmationAsker.prompts.get(0)));
    }
  }

  /**
   * Instantly modifies an option and applies the changes.
   *
   * @param option The option to modify.
   * @param value  The new value for the option.
   */
  @SuppressWarnings("unused")
  @Environment(EnvType.CLIENT)
  public void instantModifyOption(Option<?, ?> option, Object value) throws
      AccessOptionsException.OptionTypeMismatch, ValueVerificationException, AccessOptionsException.OptionNotModified {
    modifyOption(option, value);
    applyOptions();
  }

  /**
   * Instantly modifies an option and applies the changes.
   *
   * @param modId    The id of the mod that owns the option.
   * @param optionId The id of the option to modify.
   * @param value    The new value for the option.
   */
  @SuppressWarnings("unused")
  @Environment(EnvType.CLIENT)
  public void instantModifyOption(String modId, String optionId, Object value) throws
      AccessOptionsException.OptionTypeMismatch, ValueVerificationException, AccessOptionsException.OptionNotFound,
      AccessOptionsException.OptionStorageNotFound, AccessOptionsException.OptionNotModified {
    modifyOption(modId, optionId, value);
    applyOptions();
  }

  /**
   * Sets if the game should be restarted after the options are applied. Should be set to true if a RequiresGameRestart
   * reloader is in the list of reloaders to run. Should not be called directly, but through the reloaders.
   *
   * @param restartNeeded If the game should be restarted after the options are applied.
   */
  @Environment(EnvType.CLIENT)
  public void setRestartNeeded(Boolean restartNeeded) {
    this.restartNeeded = restartNeeded;
  }

  /**
   * Resets an option. Used to cancel the modification of an option in the context of this OptionsAccessHandler (before
   * the options are applied).
   *
   * @param option The option to reset.
   */
  @Environment(EnvType.CLIENT)
  public void resetOption(Option<?, ?> option) {
    option.reset();
    modifiedOptions.remove(option);
  }

  /**
   * Resets an option. Used to cancel the modification of an option in the context of this OptionsAccessHandler (before
   * the options are applied).
   *
   * @param modId    The id of the mod that owns the option.
   * @param optionId The id of the option to reset.
   */
  @SuppressWarnings("unused")
  @Environment(EnvType.CLIENT)
  public void resetOption(String modId, String optionId) {
    try {
      OptionsStorage<?> optionsStorage = getOptionsStorage(modId);
      if (optionsStorage == null) {
        throw new AccessOptionsException.OptionStorageNotFound(modId);
      }
      Option<?, ?> option = optionsStorage.getOption(optionId);
      if (option == null) {
        throw new AccessOptionsException.OptionNotFound(modId, optionId);
      }
      resetOption(option);
    } catch (AccessOptionsException.OptionStorageNotFound | AccessOptionsException.OptionNotFound e) {
      sendFeedback(e.getMessage());
    }
  }
}

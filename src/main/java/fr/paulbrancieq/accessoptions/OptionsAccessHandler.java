package fr.paulbrancieq.accessoptions;

import fr.paulbrancieq.accessoptions.commons.client.gui.screen.ReloaderConfirmationScreen;
import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.options.Option;
import fr.paulbrancieq.accessoptions.commons.reloader.AskConfirmation;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.reloader.ReloaderComparator;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import fr.paulbrancieq.accessoptions.commons.storage.StorageSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.util.*;

public class OptionsAccessHandler {
  private static final List<StorageSupplier> availableModOptionsStoragesMap = new ArrayList<>();
  private final Map<String, OptionsStorage<?>> modOptionsStoragesMap = new HashMap<>();
  private final List<Option<?>> modifiedOptions = new ArrayList<>();
  private final List<OptionsStorage<?>> modifiedStorages = new ArrayList<>();
  protected final List<Reloader> reloadersFromModifiedOptions = new ArrayList<>();
  protected final List<Reloader> reloadersToRun = new ArrayList<>();
  private Boolean restartNeeded = false;
  private ConfirmationAsker confirmationAsker;

  @Environment(EnvType.CLIENT)
  public OptionsAccessHandler() {
    for (StorageSupplier storageSupplier : availableModOptionsStoragesMap) {
      storageSupplier.supply(optionsStorage -> {
        modOptionsStoragesMap.put(optionsStorage.getStorageId(), optionsStorage);
        AccessOptions.getLogger().info("Storage \"" + optionsStorage.getStorageId() + "\" registered.");
      }, this);
    }
  }

  public static void registerModOptionsStorageSupplier(StorageSupplier supplier) {
    availableModOptionsStoragesMap.add(supplier);
  }

  @Environment(EnvType.CLIENT)
  public void instantModifyAndApplyOption(String modId, String optionId, Object value) throws
      AccessOptionsException.OptionNotFound,
      AccessOptionsException.OptionStorageNotFound,
      AccessOptionsException.OptionTypeMismatch {
    OptionsStorage<?> optionsStorage = getOptionsStorage(modId);
    if (optionsStorage == null) {
      throw new AccessOptionsException.OptionStorageNotFound(modId);
    }
    Option<?> option = optionsStorage.getOption(optionId);
    if (option == null) {
      throw new AccessOptionsException.OptionNotFound(modId, optionId);
    }
    instantModifyAndApplyOption(option, value);
  }

  @Environment(EnvType.CLIENT)
  public void instantModifyAndApplyOption(Option<?> option, Object value) throws
      AccessOptionsException.OptionTypeMismatch {
    modifyOption(option, value);
    applyAllModifiedOptions();
  }

  @Environment(EnvType.CLIENT)
  public void modifyOption(String modId, String optionId, Object value) throws
      AccessOptionsException.OptionNotFound,
      AccessOptionsException.OptionStorageNotFound,
      AccessOptionsException.OptionTypeMismatch {
    OptionsStorage<?> optionsStorage = getOptionsStorage(modId);
    if (optionsStorage == null) {
      throw new AccessOptionsException.OptionStorageNotFound(modId);
    }
    Option<?> option = optionsStorage.getOption(optionId);
    if (option == null) {
      throw new AccessOptionsException.OptionNotFound(modId, optionId);
    }
    modifyOption(option, value);
  }

  @Environment(EnvType.CLIENT)
  public void modifyOption(Option<?> option, Object value) throws AccessOptionsException.OptionTypeMismatch {
    option.reset();
    option.setValue(value);
    modifiedOptions.add(option);
  }

  @Environment(EnvType.CLIENT)
  public void applyModifiedOption(Option<?> option) throws AccessOptionsException.OptionNotModified {
    option.applyChanges();
    option.reset();
    if (!modifiedStorages.contains(option.getStorage())) {
      modifiedStorages.add(option.getStorage());
    }
  }

  public void askReloadersAndApplyModifiedOptions() {
    setPromptsReloaders();
    confirmationAsker.prompts.stream().findFirst().ifPresent(prompt ->
        MinecraftClient.getInstance().setScreen(prompt)
    );
    applyAllModifiedOptions();
  }

  @Environment(EnvType.CLIENT)
  private void setPromptsReloaders() {
    reloadersFromModifiedOptions.clear();
    modifiedOptions.forEach(option -> addReloadersFromOption(option.getReloaders(), option));
    reloadersFromModifiedOptions.sort(new ReloaderComparator());
    confirmationAsker = new ConfirmationAsker(this::applyAllModifiedOptions);
    reloadersFromModifiedOptions.forEach(reloader -> {
      if (reloader instanceof AskConfirmation) {
        confirmationAsker.addPrompt(reloader);
      } else {
        addOneReloaderToRun(reloader);
      }
    });
  }

  @Environment(EnvType.CLIENT)
  private void addReloadersFromOption(Collection<Reloader> newReloadersToRun, Option<?> option) {
    newReloadersToRun.forEach(reloader -> addOneReloaderFromOption(reloader, option));
  }

  @Environment(EnvType.CLIENT)
  private void addOneReloaderFromOption(Reloader newReloader, Option<?> option) {
    reloadersFromModifiedOptions.stream().filter(newReloader::isSameAs).findFirst().ifPresentOrElse(
        reloaderToRun -> reloaderToRun.addAssociatedModifiedOption(option),
        () -> {
          newReloader.addAssociatedModifiedOption(option);
          reloadersFromModifiedOptions.add(newReloader);
        }
    );
  }

  @Environment(EnvType.CLIENT)
  private void addOneReloaderToRun(Reloader newReloader) {
    if (reloadersToRun.stream().noneMatch(newReloader::isSameAs) &&
        reloadersToRun.stream().noneMatch(newReloader::isChildOf)) {
      reloadersToRun.removeIf(reloader -> reloader.isChildOf(newReloader));
      reloadersToRun.add(newReloader);
    }
  }

  @Environment(EnvType.CLIENT)
  private class ConfirmationAsker {
    private final Runnable runAfterAllConfirmations;
    private final List<ReloaderConfirmationScreen> prompts = new ArrayList<>();

    protected ConfirmationAsker(Runnable runAfterConfirmation) {
      this.runAfterAllConfirmations = runAfterConfirmation;
    }

    protected void addPrompt(Reloader reloader) {
      if (!(reloader instanceof AskConfirmation)) {
        AccessOptions.getLogger().warn("Reloader " + reloader.getClass().getName() + " is not an AskConfirmation");
        return;
      }
      ReloaderConfirmationScreen prompt = new ReloaderConfirmationScreen((confirmationResult) -> {
        if (confirmationResult) {
          addOneReloaderToRun(reloader);
        }
        reloader.getAssociatedModifiedOptions().forEach(option -> {
          ((AskConfirmation) reloader).getPromptAnswerConsumer(option).accept(confirmationResult);
        });
        runAfterAllConfirmations.run();
      }, ((AskConfirmation) reloader).getName(), ((AskConfirmation) reloader).getConfirmationText(), reloader);
      if (!prompts.isEmpty()) {
        prompts.get(prompts.size() - 1).setCallback((confirmationResult) -> {
          if (confirmationResult) {
            addOneReloaderToRun(reloader);
          }
          reloader.getAssociatedModifiedOptions().forEach(option ->
              ((AskConfirmation) reloader).getPromptAnswerConsumer(option).accept(confirmationResult)
          );
        });
      }
      prompts.add(prompt);
    }
  }

  @Environment(EnvType.CLIENT)
  private void applyAllModifiedOptions() {
    for (Option<?> option : modifiedOptions) {
      try {
        applyModifiedOption(option);
      } catch (AccessOptionsException.OptionNotModified e) {
        AccessOptions.getLogger().warn(e.getMessage());
      }
    }
    modifiedStorages.forEach(OptionsStorage::save);
    runReloaders();
    modifiedOptions.clear();
    modifiedStorages.clear();
    restartIfNeeded();
  }

  @Environment(EnvType.CLIENT)
  public void setRestartNeeded(Boolean restartNeeded) {
    this.restartNeeded = restartNeeded;
  }

  @Environment(EnvType.CLIENT)
  private void restartIfNeeded() {
    if (restartNeeded) {
      MinecraftClient.getInstance().stop();
    }
  }

  @Environment(EnvType.CLIENT)
  public void resetOption(Option<?> option) {
    option.reset();
    modifiedOptions.remove(option);
  }

  @Environment(EnvType.CLIENT)
  private void runReloaders() {
    reloadersFromModifiedOptions.forEach(Reloader::run);
    reloadersFromModifiedOptions.clear();
  }

  private OptionsStorage<?> getOptionsStorage(String id) {
    return modOptionsStoragesMap.get(id);
  }
}

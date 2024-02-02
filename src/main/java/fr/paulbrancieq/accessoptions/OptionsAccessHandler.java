package fr.paulbrancieq.accessoptions;

import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.options.Option;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

import java.util.*;
import java.util.stream.Collectors;

public class OptionsAccessHandler {
  private static final Map<String, OptionsStorage<?>> modOptionsStoragesMap = new HashMap<>();
  private static final List<Option<?>> modifiedOptions = new ArrayList<>();
  private static final List<OptionsStorage<?>> modifiedStorages = new ArrayList<>();
  private static final List<Reloader> reloadersToRun = new ArrayList<>();

  public static void instantModifyAndApplyOption(String modId, String optionId, Object value) throws
      AccessOptionsException.OptionNotFound,
      AccessOptionsException.OptionStorageNotFound,
      AccessOptionsException.OptionTypeMismatch,
      AccessOptionsException.OptionNotModified {
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

  public static void instantModifyAndApplyOption(Option<?> option, Object value) throws
      AccessOptionsException.OptionTypeMismatch,
      AccessOptionsException.OptionNotModified {
    modifyOption(option, value);
    instantApplyModifiedOption(option);
  }

  public static void modifyOption(String modId, String optionId, Object value) throws
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

  public static void modifyOption(Option<?> option, Object value) throws AccessOptionsException.OptionTypeMismatch {
    option.reset();
    option.setValue(value);
    modifiedOptions.add(option);
  }

  public static void applyModifiedOption(Option<?> option) throws AccessOptionsException.OptionNotModified {
    option.applyChanges();
    option.reset();
    addReloadersToRun(option.getReloaders());
    if (!modifiedStorages.contains(option.getStorage())) {
      modifiedStorages.add(option.getStorage());
    }
  }

  private static void addReloadersToRun(Collection<Reloader> newReloadersToRun) {
    Collection<Reloader> filteredNewReloadersToRun = newReloadersToRun.stream().filter(reloader ->
        reloadersToRun.stream().noneMatch(reloader::isChildOf)).toList();
    filteredNewReloadersToRun.forEach(OptionsAccessHandler::addOneReloaderToRun);
  }

  private static void addOneReloaderToRun(Reloader reloader) {
    reloadersToRun.removeIf(reloaderToRun -> reloaderToRun.isChildOf(reloader));
    reloadersToRun.add(reloader);
  }

  public static void instantApplyModifiedOption(Option<?> option) throws AccessOptionsException.OptionNotModified {
    applyModifiedOption(option);
    option.getStorage().save();
  }

  public static void applyAllModifiedOptions() {
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
  }

  public static void runReloaders() {
    reloadersToRun.forEach(Reloader::run);
    reloadersToRun.clear();
  }

  public static void registerModOptionsStorage(OptionsStorage<?> optionStorage) {
    modOptionsStoragesMap.put(optionStorage.getModId(), optionStorage);
  }

  public static OptionsStorage<?> getOptionsStorage(String id) {
    return modOptionsStoragesMap.get(id);
  }
}

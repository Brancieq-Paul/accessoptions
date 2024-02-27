package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OptionsStorageImpl<T> implements OptionsStorage<T> {
  private final Map<String, OptionImpl<T, ?>> options = new HashMap<>();
  private final String storageId;
  private final Supplier<T> getData;
  private final Runnable save;
  private final OptionsAccessHandler optionsAccessHandler;

  public OptionsStorageImpl(String storageId, Supplier<T> getData, Runnable save, Map<String, OptionImpl.Builder<T, ?, ?>> optionsBuilders, OptionsAccessHandler optionsAccessHandler) {
    this.storageId = storageId;
    this.getData = getData;
    this.save = save;
    this.optionsAccessHandler = optionsAccessHandler;
    for (Map.Entry<String, OptionImpl.Builder<T, ?, ?>> entry : optionsBuilders.entrySet()) {
      registerOption(entry.getKey(), entry.getValue().setStorage(this).build());
    }
  }

  @Override
  public String getStorageId() {
    return storageId;
  }

  @Override
  public void registerOption(String optionId, OptionImpl<T, ?> option) {
    options.put(optionId, option);
  }

  @Override
  public OptionImpl<T, ?> getOption(String id) {
    return options.get(id);
  }

  @Override
  public T getData() {
    return getData.get();
  }

  @Override
  public void save() {
    save.run();
  }

  @Override
  public OptionsAccessHandler getOptionsAccessHandler() {
    return optionsAccessHandler;
  }
}

package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.commons.options.Option;

import java.util.HashMap;
import java.util.Map;

public abstract class OptionsStorageImpl<T> implements OptionsStorage<T> {
  private final Map<String, Option<T, ?>> options = new HashMap<>();

  private final String storageId;

  public OptionsStorageImpl(String storageId) {
    this.storageId = storageId;
  }

  @Override
  public String getStorageId() {
    return storageId;
  }

  @Override
  public void registerOption(String optionId, Option<T, ?> option) {
    options.put(optionId, option);
  }

  @Override
  public Option<T, ?> getOption(String id) {
    return options.get(id);
  }
}

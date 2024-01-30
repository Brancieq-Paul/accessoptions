package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.commons.options.Option;

import java.util.HashMap;
import java.util.Map;

public abstract class OptionsStorageImpl<T> implements OptionsStorage<T> {
  private final Map<String, Option<?>> options = new HashMap<>();

  private final String modId;

  public OptionsStorageImpl(String modId) {
    this.modId = modId;
  }

  @Override
  public String getModId() {
    return modId;
  }

  @Override
  public void registerOption(String optionId, Option<?> option) {
    options.put(optionId, option);
  }

  @Override
  public Option<?> getOption(String id) {
    return options.get(id);
  }
}

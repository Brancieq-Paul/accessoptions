package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

public class RangedNumberOption<S> extends RangedOption<S, Number> {
  public RangedNumberOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Number> {
    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
    }

    @Override
    public RangedNumberOption<S> build() {
      return new RangedNumberOption<>(this);
    }
  }
}

package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

public abstract class RangedOption<S, T> extends OptionImpl<S, T> implements Ranged<T> {

  protected final T min;
  protected final T max;

  protected RangedOption(Builder<S, T> builder) {
    super(builder);
    this.min = builder.min;
    this.max = builder.max;
  }

  @SuppressWarnings("unused")
  @Override
  public T getMin() {
    return min;
  }

  @SuppressWarnings("unused")
  @Override
  public T getMax() {
    return max;
  }

  public static class Builder<S, T> extends OptionImpl.Builder<S, T> {
    protected T min;
    protected T max;

    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
    }

    public Builder<S, T> setRange(T min, T max) {
      this.min = min;
      this.max = max;
      return this;
    }
  }
}

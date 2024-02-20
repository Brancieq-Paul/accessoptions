package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

public abstract class RangedOption<S, T> extends OptionImpl<S, T> implements Ranged<T> {

  protected final T min;
  protected final T max;

  protected RangedOption(Builder<S, T, ?> builder) {
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

  public static class Builder<S, T, U extends Builder<S, T, ?>> extends OptionImpl.Builder<S, T, Builder<S, T, ?>> {
    protected T min;
    protected T max;

    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
    }

    @SuppressWarnings("unchecked")
    public U setRange(T min, T max) {
      this.min = min;
      this.max = max;
      return (U) this;
    }

    @Override
    public RangedOption<S, T> build() {
      throw new UnsupportedOperationException("Non implemented for abstract RangedOption.");
    }
  }
}

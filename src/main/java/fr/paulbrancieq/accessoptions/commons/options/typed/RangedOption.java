package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;

import java.util.function.Supplier;

public abstract class RangedOption<S, T> extends OptionImpl<S, T> implements Ranged<T> {

  protected final Supplier<T> min;
  protected final Supplier<T> max;

  protected RangedOption(Builder<S, T, ?> builder) {
    super(builder);
    this.min = builder.min;
    this.max = builder.max;
  }

  @SuppressWarnings("unused")
  @Override
  public T getMin() {
    return min.get();
  }

  @SuppressWarnings("unused")
  @Override
  public T getMax() {
    return max.get();
  }

  public static class Builder<S, T, U extends Builder<S, T, ?>> extends OptionImpl.Builder<S, T, Builder<S, T, ?>> {
    protected Supplier<T> min;
    protected Supplier<T> max;

    protected Builder(String optionId) {
      super(optionId);
    }

    @SuppressWarnings("unchecked")
    public U setRange(T min, T max) {
      this.min = () -> min;
      this.max = () -> max;
      return (U) this;
    }

    @SuppressWarnings("unchecked")
    public U setRange(Supplier<T> min, Supplier<T> max) {
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

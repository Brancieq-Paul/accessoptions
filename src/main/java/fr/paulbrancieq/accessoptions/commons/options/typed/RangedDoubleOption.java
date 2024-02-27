package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;

public class RangedDoubleOption<S> extends RangedOption<S, Double> {
  protected static ModificationInputTransformer<String, Double> inputToValueTransformer = Double::parseDouble;

  public RangedDoubleOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createRangedDoubleBuilder(String optionId, @SuppressWarnings("unused") Class<S> storageRawType) {
    return new Builder<>(optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Double, RangedDoubleOption.Builder<S>> {
    protected Builder(String optionId) {
      super(optionId);
      setValueVerifier((value) -> {
        if (!(value >= min.get() && value <= max.get())) {
          throw new ValueVerificationException.ValueNotInRange(storage.getStorageId(), optionId, value, min.get(), max.get());
        }
      });
      addInputToValueTransformers(inputToValueTransformer);
    }

    @Override
    public RangedDoubleOption<S> build() {
      return new RangedDoubleOption<>(this);
    }
  }
}

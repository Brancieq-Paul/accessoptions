package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

public class RangedDoubleOption<S> extends RangedOption<S, Double> {
  protected static ModificationInputTransformer<String, Double> inputToValueTransformer = Double::parseDouble;

  public RangedDoubleOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Double, RangedDoubleOption.Builder<S>> {
    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
      setValueVerifier((value) -> {
        if (!(value >= min && value <= max)) {
          throw new ValueVerificationException.ValueNotInRange(storage.getStorageId(), optionId, value, min, max);
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

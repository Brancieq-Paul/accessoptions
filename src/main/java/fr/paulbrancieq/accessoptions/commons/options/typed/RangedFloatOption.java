package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

public class RangedFloatOption<S> extends RangedOption<S, Float> {
  protected static ModificationInputTransformer<String, Float> inputToValueTransformer = Float::parseFloat;

  public RangedFloatOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createRangedFloatBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Float, RangedFloatOption.Builder<S>> {
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
    public RangedFloatOption<S> build() {
      return new RangedFloatOption<>(this);
    }
  }
}

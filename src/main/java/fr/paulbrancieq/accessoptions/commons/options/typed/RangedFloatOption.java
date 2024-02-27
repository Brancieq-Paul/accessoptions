package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;

@SuppressWarnings("unused")
public class RangedFloatOption<S> extends RangedOption<S, Float> {
  protected static ModificationInputTransformer<String, Float> inputToValueTransformer = Float::parseFloat;

  public RangedFloatOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createRangedFloatBuilder(String optionId, @SuppressWarnings("unused") Class<S> storageRawType) {
    return new Builder<>(optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Float, RangedFloatOption.Builder<S>> {
    protected Builder(String optionId) {
      super(optionId);
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

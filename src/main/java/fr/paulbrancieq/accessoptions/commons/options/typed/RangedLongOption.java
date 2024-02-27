package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;

@SuppressWarnings("unused")
public class RangedLongOption<S> extends RangedOption<S, Long> {
  protected static ModificationInputTransformer<String, Long> inputToValueTransformer = Long::parseLong;

  public RangedLongOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createRangedLongBuilder(String optionId, @SuppressWarnings("unused") Class<S> storageRawType) {
    return new Builder<>(optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Long, RangedLongOption.Builder<S>> {
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
    public RangedLongOption<S> build() {
      return new RangedLongOption<>(this);
    }
  }
}

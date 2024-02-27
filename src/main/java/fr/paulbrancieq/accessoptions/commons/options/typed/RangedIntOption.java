package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;

public class RangedIntOption<S> extends RangedOption<S, Integer> {
  protected static ModificationInputTransformer<String, Integer> inputToValueTransformer = Integer::parseInt;
  public RangedIntOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createRangedIntBuilder(String optionId, @SuppressWarnings("unused") Class<S> storageRawType) {
    return new Builder<>(optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Integer, RangedIntOption.Builder<S>> {
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
    public RangedIntOption<S> build() {
      return new RangedIntOption<>(this);
    }
  }
}

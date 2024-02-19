package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

public class RangedIntOption<S> extends RangedOption<S, Integer> {
  protected static ModificationInputTransformer<String, Integer> inputToValueTransformer = Integer::parseInt;
  public RangedIntOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Integer, RangedIntOption.Builder<S>> {
    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
      setValueVerifier((value) -> {
        if (!(value >= min && value <= max)) {
          throw new ValueVerificationException.ValueNotInRange(storage.getStorageId(), optionId, value, min, max);
        }
      });
    }

    @Override
    public RangedIntOption<S> build() {
      super.setInputToValueTransformers(inputToValueTransformer);
      return new RangedIntOption<>(this);
    }
  }
}

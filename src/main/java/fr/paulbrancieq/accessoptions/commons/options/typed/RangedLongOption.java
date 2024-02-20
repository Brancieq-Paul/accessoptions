package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

public class RangedLongOption<S> extends RangedOption<S, Long> {
  protected static ModificationInputTransformer<String, Long> inputToValueTransformer = Long::parseLong;

  public RangedLongOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Long, RangedLongOption.Builder<S>> {
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
    public RangedLongOption<S> build() {
      return new RangedLongOption<>(this);
    }
  }
}

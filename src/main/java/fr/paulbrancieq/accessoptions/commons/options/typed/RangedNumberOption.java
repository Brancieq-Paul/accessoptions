package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

import java.text.NumberFormat;

public class RangedNumberOption<S> extends RangedOption<S, Number> {
  public RangedNumberOption(Builder<S> builder) {
    super(builder);
  }

  public static <S> Builder<S> createBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S> extends RangedOption.Builder<S, Number, Builder<S>> {
    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
      setValueVerifier((value) -> {
        if (!(value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue())) {
          throw new ValueVerificationException.ValueNotInRange(storage.getStorageId(), optionId, value, min, max);
        }
      });
    }

    @Override
    public RangedNumberOption<S> build() {
      super.setInputToValueTransformers((input) -> {
        if (input instanceof Number) {
          return (Number) input;
        } else {
          return NumberFormat.getInstance().parse(input.toString());
        }
      });
      return new RangedNumberOption<>(this);
    }
  }
}

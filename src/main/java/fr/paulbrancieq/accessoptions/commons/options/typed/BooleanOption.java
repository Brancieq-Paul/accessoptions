package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

import java.util.HashMap;

public class BooleanOption<S> extends EnumOption<S, Boolean> {
  protected BooleanOption(Builder<S> builder) {
    super(builder);
  }
  public static <S> Builder<S> createBooleanBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  public static class Builder<S> extends EnumOption.Builder<S, Boolean, Builder<S>> {
    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
      addAssociationMap(new HashMap<>() {
        {
          put("true", true);
          put("false", false);
          put("1", true);
          put("0", false);
          put("yes", true);
          put("no", false);
          put("on", true);
          put("off", false);
        }
      });
    }

    @Override
    public BooleanOption<S> build() {
      return new BooleanOption<>(this);
    }
  }
}

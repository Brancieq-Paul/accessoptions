package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.options.TranslationValueMap;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnumOption<S, T> extends OptionImpl<S, T> {
  protected TranslationValueMap<T> valueMap;

  public EnumOption(Builder<S, T, ?> builder) {
    super(builder);
    this.valueMap = builder.valueMap;
  }

  public static <S, T> Builder<S, T, ?> createEnumBuilder(@SuppressWarnings("unused") Class<T> type, @SuppressWarnings("unused") Class<S> storageRawType, String optionId) {
    return new Builder<>(optionId);
  }

  @SuppressWarnings({"unchecked"})
  public static class Builder<S, T, U extends Builder<S, T, ?>> extends OptionImpl.Builder<S, T, Builder<S, T, ?>> {
    protected TranslationValueMap<T> valueMap = new TranslationValueMap<>();

    protected Builder(String optionId) {
      super(optionId);
      setValueVerifier(value -> {
        if (!valueMap.containsValue(value)) {
          throw new ValueVerificationException.ValueNotInEnum(storage.getStorageId(), optionId, value,
              valueMap.values().toArray());
        }
      });
      addInputToValueTransformers((ModificationInputTransformer<String, T>) (input) -> {
        T value = valueMap.get(input.toLowerCase());
        if (value == null) {
          throw new IllegalArgumentException("Invalid enum value: " + input);
        }
        return value;
      });
    }

    @SuppressWarnings("UnusedReturnValue")
    public U addAssociation(String key, T value, Boolean default_lang) {
      valueMap.put(key, value, default_lang);
      return (U) this;
    }

    @SuppressWarnings({"unused","UnusedReturnValue"})
    public U addTranslatedAssociation(String translation_key, T value) {
      addAssociation(translation_key, value, true);
      addAssociation(translation_key, value, false);
      return (U) this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public U addAssociationMap(Map<String, T> valueMap) {
      this.valueMap.putAll(valueMap);
      return (U) this;
    }

    public U addTranslatedAssociationMap(LinkedHashMap<String, T> valueMap) {
      valueMap.forEach(this::addTranslatedAssociation);
      return (U) this;
    }

    @Override
    public EnumOption<S, T> build() {
      return new EnumOption<>(this);
    }
  }
}

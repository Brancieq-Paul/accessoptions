package fr.paulbrancieq.accessoptions.commons.options.typed;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.options.ModificationInputTransformer;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnumOption<S, T> extends OptionImpl<S, T> {
  protected Map<String, T> valueMap;

  public EnumOption(Builder<S, T, ?> builder) {
    super(builder);
    this.valueMap = builder.valueMap;
  }

  public static <S, T> Builder<S, T, ?> createBuilder(OptionsStorage<S> storage, String optionId) {
    return new Builder<>(storage, optionId);
  }

  @SuppressWarnings({"unchecked"})
  public static class Builder<S, T, U extends Builder<S, T, ?>> extends OptionImpl.Builder<S, T, Builder<S, T, ?>> {
    protected Map<String, T> valueMap = new HashMap<>();
    TranslationStorage translationStorage = TranslationStorage.load(MinecraftClient.getInstance().getResourceManager(),
        new ArrayList<>() {{add("en_us");}}, false);

    protected Builder(OptionsStorage<S> storage, String optionId) {
      super(storage, optionId);
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
    public U addAssociation(String key, T value) {
      valueMap.put(key.toLowerCase(), value);
      return (U) this;
    }

    @SuppressWarnings({"unused","UnusedReturnValue"})
    public U addTranslatedAssociation(String translation_key, T value, String language) {
      if (!language.equals("en_us")) {
        addAssociation(translationStorage.get(translation_key), value);
      }
      addAssociation(I18n.translate(translation_key), value);
      return (U) this;
    }

    @SuppressWarnings("unused")
    public U addAssociationMap(Map<String, T> valueMap) {
      this.valueMap.putAll(valueMap);
      return (U) this;
    }

    @SuppressWarnings("unused")
    public U addTranslatedAssociationMap(Map<String, T> valueMap, String language) {
      valueMap.forEach((key, value) -> addTranslatedAssociation(key, value, language));
      return (U) this;
    }

    @Override
    public EnumOption<S, T> build() {
      return new EnumOption<>(this);
    }
  }
}

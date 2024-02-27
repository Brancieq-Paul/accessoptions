package fr.paulbrancieq.accessoptions.commons.options;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;

import java.util.ArrayList;

public class TranslatableString {
  TranslationStorage translationStorage;
  String translationKey;
  Boolean defaultLang = false;

  public TranslatableString(String key) {
    this.translationKey = key;
  }

  public TranslatableString(String key, Boolean defaultLang) {
    this.translationKey = key;
    this.defaultLang = defaultLang;
  }

  private void initIfNeeded() {
    if (translationStorage == null) {
      translationStorage = TranslationStorage.load(MinecraftClient.getInstance().getResourceManager(),
          new ArrayList<>() {{add("en_us");}}, false);
    }
  }

  public String get() {
    initIfNeeded();
    if (defaultLang) {
      return translationStorage.get(translationKey);
    }
    return I18n.translate(translationKey);
  }
}

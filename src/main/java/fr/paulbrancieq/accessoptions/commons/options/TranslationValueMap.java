package fr.paulbrancieq.accessoptions.commons.options;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TranslationValueMap<T> {
  private final Map<TranslatableString, T> map = new HashMap<>();

  public void put(TranslatableString key, T value) {
    map.put(key, value);
  }

  public void put(String key, T value, Boolean default_lang) {
    put(new TranslatableString(key, default_lang), value);
  }

  public void putAll(Map<String, T> other) {
    other.forEach((key, value) -> map.put(new TranslatableString(key), value));
  }

  public T get(String key) {
    for (TranslatableString translatableString : map.keySet()) {
      if (translatableString.get().toLowerCase().equals(key)) {
        return map.get(translatableString);
      }
    }
    return null;
  }

  public boolean containsValue(T value) {
    return map.containsValue(value);
  }

  @SuppressWarnings("unused")
  public boolean containsKey(String key) {
    for (TranslatableString translatableString : map.keySet()) {
      if (translatableString.get().toLowerCase().equals(key)) {
        return true;
      }
    }
    return false;
  }

  public Collection<T> values() {
    return map.values();
  }
}

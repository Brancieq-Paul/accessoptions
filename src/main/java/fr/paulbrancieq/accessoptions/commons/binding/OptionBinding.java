package fr.paulbrancieq.accessoptions.commons.binding;

public interface OptionBinding<S, T> {
  void setValue(S storage, T value);

  T getValue(S storage);
}

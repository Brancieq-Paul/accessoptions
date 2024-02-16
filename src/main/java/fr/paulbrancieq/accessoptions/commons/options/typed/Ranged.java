package fr.paulbrancieq.accessoptions.commons.options.typed;

public interface Ranged<T> {
  @SuppressWarnings("unused")
  T getMin();

  @SuppressWarnings("unused")
  T getMax();
}

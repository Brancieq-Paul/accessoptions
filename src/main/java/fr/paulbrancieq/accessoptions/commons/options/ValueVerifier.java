package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;

public interface ValueVerifier<T> {
  void accept(T t) throws ValueVerificationException;
}

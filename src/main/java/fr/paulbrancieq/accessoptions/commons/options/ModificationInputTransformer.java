package fr.paulbrancieq.accessoptions.commons.options;

import java.lang.reflect.ParameterizedType;

@FunctionalInterface
public interface ModificationInputTransformer<T, U> {
  @SuppressWarnings("unchecked")
  default Class<T> getInputType() {
    return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
  }
  U apply(T t) throws Exception;
}

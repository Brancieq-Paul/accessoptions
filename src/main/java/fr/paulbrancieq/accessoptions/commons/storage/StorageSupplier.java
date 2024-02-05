package fr.paulbrancieq.accessoptions.commons.storage;

import java.util.function.Consumer;

public interface StorageSupplier {
  void supply(Consumer<OptionsStorage<?>> storageConsumer);
}

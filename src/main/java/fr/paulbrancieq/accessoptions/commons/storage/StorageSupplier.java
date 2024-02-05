package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;

import java.util.function.Consumer;

public interface StorageSupplier {
  void supply(Consumer<OptionsStorage<?>> storageConsumer, OptionsAccessHandler optionsAccessHandler);
}

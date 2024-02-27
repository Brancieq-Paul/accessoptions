package fr.paulbrancieq.accessoptions.commons.storage.suppliers;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

import java.util.function.Consumer;

public interface StorageSupplier {
  void supply(Consumer<OptionsStorage<?>> storageConsumer, OptionsAccessHandler optionsAccessHandler);
}

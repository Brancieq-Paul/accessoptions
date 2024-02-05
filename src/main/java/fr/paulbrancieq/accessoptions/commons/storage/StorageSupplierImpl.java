package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Consumer;
import java.util.function.Function;

public class StorageSupplierImpl implements StorageSupplier {
  private final Function<OptionsAccessHandler, OptionsStorage<?>> storageSupplier;
  private final String modId;
  public StorageSupplierImpl(Function<OptionsAccessHandler, OptionsStorage<?>> storageSupplier, String modId) {
    this.storageSupplier = storageSupplier;
    this.modId = modId;
  }

  public void supply(Consumer<OptionsStorage<?>> storageConsumer, OptionsAccessHandler optionsAccessHandler) {
    if (FabricLoader.getInstance().isModLoaded(this.modId)) {
      AccessOptions.getLogger().info("Mod " + this.modId + " is loaded, supplying storage");
      storageConsumer.accept(this.storageSupplier.apply(optionsAccessHandler));
    }
    AccessOptions.getLogger().info("Mod " + this.modId + " is not loaded, can't supply storage");
  }
}

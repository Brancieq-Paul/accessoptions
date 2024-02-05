package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.AccessOptions;
import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StorageSupplierImpl implements StorageSupplier {
  private final Supplier<OptionsStorage<?>> storageSupplier;
  private final String modId;
  public StorageSupplierImpl(Supplier<OptionsStorage<?>> storageSupplier, String modId) {
    this.storageSupplier = storageSupplier;
    this.modId = modId;
  }

  public void supply(Consumer<OptionsStorage<?>> storageConsumer) {
    if (FabricLoader.getInstance().isModLoaded(this.modId)) {
      AccessOptions.getLogger().info("Mod " + this.modId + " is loaded, supplying storage");
      storageConsumer.accept(this.storageSupplier.get());
    }
    AccessOptions.getLogger().info("Mod " + this.modId + " is not loaded, can't supply storage");
  }
}

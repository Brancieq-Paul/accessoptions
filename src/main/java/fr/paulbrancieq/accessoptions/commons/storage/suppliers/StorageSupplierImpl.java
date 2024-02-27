package fr.paulbrancieq.accessoptions.commons.storage.suppliers;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Consumer;
import java.util.function.Function;

public class StorageSupplierImpl implements StorageSupplier {
  protected Function<OptionsAccessHandler, OptionsStorage<?>> storageSupplier;
  private final String modId;
  public StorageSupplierImpl(Function<OptionsAccessHandler, OptionsStorage<?>> storageSupplier, String modId) {
    this.storageSupplier = storageSupplier;
    this.modId = modId;
  }

  public void supply(Consumer<OptionsStorage<?>> storageConsumer, OptionsAccessHandler optionsAccessHandler) {
    if (FabricLoader.getInstance().isModLoaded(this.modId)) {
      AccessOptions.getLogger().info("Mod " + this.modId + " is loaded, supplying storage");
      storageConsumer.accept(this.storageSupplier.apply(optionsAccessHandler));
      return;
    }
    AccessOptions.getLogger().info("Mod " + this.modId + " is not loaded, can't supply storage");
  }
}

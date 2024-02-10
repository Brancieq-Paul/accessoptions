package fr.paulbrancieq.accessoptions;

import fr.paulbrancieq.accessoptions.commons.storage.MinecraftOptionsStorage;
import fr.paulbrancieq.accessoptions.commons.storage.SodiumOptionsStorage;
import fr.paulbrancieq.accessoptions.commons.storage.StorageSupplierImpl;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessOptions implements ModInitializer {
  @SuppressWarnings({"SpellCheckingInspection", "unused"})
  public static final String MOD_ID = "accessoptions";
  public static final String MOD_NAME = "Access Options";
  private static Logger LOGGER;

  @Override
  public void onInitialize() {
    OptionsAccessHandler.registerModOptionsStorageSupplier(
        new StorageSupplierImpl(
            MinecraftOptionsStorage::new,
            "minecraft"
        )
    );
    OptionsAccessHandler.registerModOptionsStorageSupplier(
        new StorageSupplierImpl(
            SodiumOptionsStorage::new,
            "sodium"
        )
    );
  }

  public static Logger getLogger() {
    if (LOGGER == null) {
      LOGGER = LoggerFactory.getLogger(MOD_NAME)  ;
    }
    return LOGGER;
  }
}

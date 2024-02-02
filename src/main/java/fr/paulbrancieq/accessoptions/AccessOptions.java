package fr.paulbrancieq.accessoptions;

import fr.paulbrancieq.accessoptions.commons.storage.MinecraftOptionsStorage;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;

public class AccessOptions implements ModInitializer {
  public static final String MOD_ID = "accessoptions";
  public static final String MOD_NAME = "Access Options";
  private static AccessOptions INSTANCE;
  private static Logger LOGGER;

  @Override
  public void onInitialize() {
    INSTANCE = this;
    OptionsAccessHandler.registerModOptionsStorage(new MinecraftOptionsStorage());
  }

  public static AccessOptions getInstance() {
    return INSTANCE;
  }

  public static Logger getLogger() {
    if (LOGGER == null) {
      LOGGER = LoggerFactory.getLogger(MOD_NAME)  ;
    }
    return LOGGER;
  }
}

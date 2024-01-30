package fr.paulbrancieq.accessoptions;

import fr.paulbrancieq.accessoptions.commons.storage.MinecraftOptionsStorage;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;

public class AccessOptions implements ModInitializer {
  public static final String MOD_ID = "confeats";
  public static final String MOD_NAME = "Confeats";
  private static AccessOptions INSTANCE;
  private final Map<String, OptionsStorage<?>> modOptionsStoragesMap = new HashMap<>();
  private static Logger LOGGER;

  @Override
  public void onInitialize() {
    INSTANCE = this;
    registerModOptionsStorage(new MinecraftOptionsStorage());
  }

  public static AccessOptions getInstance() {
    return INSTANCE;
  }

  public void registerModOptionsStorage(OptionsStorage<?> optionStorage) {
    modOptionsStoragesMap.put(optionStorage.getModId(), optionStorage);
  }

  public OptionsStorage<?> getOptionsStorage(String id) {
    return modOptionsStoragesMap.get(id);
  }

  public Map<String, OptionsStorage<?>> getOptionsStorages() {
    return modOptionsStoragesMap;
  }

  public static Logger getLogger() {
    if (LOGGER == null) {
      LOGGER = LoggerFactory.getLogger(MOD_NAME)  ;
    }
    return LOGGER;
  }
}

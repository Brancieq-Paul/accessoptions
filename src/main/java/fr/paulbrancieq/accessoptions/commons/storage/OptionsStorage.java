package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.commons.options.Option;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface OptionsStorage<T> {
  @Environment(EnvType.CLIENT)
  T getData();
  @Environment(EnvType.CLIENT)
  void save();
  String getStorageId();
  void registerOption(String id, Option<T, ?> option);
  Option<T, ?> getOption(String id);
}

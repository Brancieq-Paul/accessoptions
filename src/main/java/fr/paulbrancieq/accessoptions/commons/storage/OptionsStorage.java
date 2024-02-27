package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface OptionsStorage<T> {
  @Environment(EnvType.CLIENT)
  T getData();
  @Environment(EnvType.CLIENT)
  void save();
  String getStorageId();
  void registerOption(String id, OptionImpl<T, ?> option);
  OptionImpl<T, ?> getOption(String id);
  OptionsAccessHandler getOptionsAccessHandler();
}

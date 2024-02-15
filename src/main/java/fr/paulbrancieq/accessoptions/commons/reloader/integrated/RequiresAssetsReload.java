package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import net.minecraft.client.MinecraftClient;

import java.util.List;

@SuppressWarnings("unused")
public class RequiresAssetsReload extends GenericReloader {
  /**
   * Create an empty instance. Used for internal purposes.
   * All reloaders should at least have this empty constructor.
   */
  @SuppressWarnings("unused")
  public RequiresAssetsReload() {
    super();
  }
  public RequiresAssetsReload(OptionsAccessHandler handler) {
    super(() -> {
      MinecraftClient client = MinecraftClient.getInstance();
      client.setMipmapLevels(client.options.getMipmapLevels().getValue());
      client.reloadResourcesConcurrently();
    }, handler);
  }
  @Override
  public List<Class<? extends Reloader>> getDirectParents() {
    return List.of(RequiresGameRestart.class);
  }
}

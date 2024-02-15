package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class RequiresRendererReload extends GenericReloader {
  /**
   * Create an empty instance. Used for internal purposes.
   * All reloaders should at least have this empty constructor.
   */
  @SuppressWarnings("unused")
  public RequiresRendererReload() {
    super();
  }
  public RequiresRendererReload(OptionsAccessHandler handler) {
    super(() ->
        MinecraftClient.getInstance().execute(
            () -> MinecraftClient.getInstance().worldRenderer.reload()
        ), handler);
  }
  @Override
  public List<Class<? extends Reloader>> getDirectParents() {
    return List.of(RequiresGameRestart.class);
  }
}

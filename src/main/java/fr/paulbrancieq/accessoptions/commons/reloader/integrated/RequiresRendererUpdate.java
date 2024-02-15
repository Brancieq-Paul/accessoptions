package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import net.minecraft.client.MinecraftClient;

public class RequiresRendererUpdate extends GenericReloader {
  /**
   * Create an empty instance. Used for internal purposes.
   * All reloaders should at least have this empty constructor.
   */
  @SuppressWarnings("unused")
  public RequiresRendererUpdate() {
    super();
  }
  public RequiresRendererUpdate(OptionsAccessHandler handler) {
    super(() -> MinecraftClient.getInstance().worldRenderer.scheduleTerrainUpdate(),
        handler);
  }
}

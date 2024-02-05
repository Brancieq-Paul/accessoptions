package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import net.minecraft.client.MinecraftClient;

public class RequiresRendererUpdate extends RequiresRendererReload {
  public RequiresRendererUpdate(OptionsAccessHandler handler) {
    super(handler);
    setRunnable(() -> MinecraftClient.getInstance().worldRenderer.scheduleTerrainUpdate());
  }
}

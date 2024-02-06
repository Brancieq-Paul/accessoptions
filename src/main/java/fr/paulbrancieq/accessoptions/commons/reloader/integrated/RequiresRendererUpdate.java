package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import net.minecraft.client.MinecraftClient;

public class RequiresRendererUpdate extends GenericReloader {
  public RequiresRendererUpdate(OptionsAccessHandler handler) {
    super(() -> MinecraftClient.getInstance().worldRenderer.scheduleTerrainUpdate(),
        handler, RequiresRendererReload.class);
  }
}

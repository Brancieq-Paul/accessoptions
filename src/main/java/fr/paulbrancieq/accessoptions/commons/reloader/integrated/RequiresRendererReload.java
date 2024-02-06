package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import net.minecraft.client.MinecraftClient;

public class RequiresRendererReload extends GenericReloader {
  public RequiresRendererReload(OptionsAccessHandler handler) {
    super(() ->
        MinecraftClient.getInstance().execute(
            () -> MinecraftClient.getInstance().worldRenderer.reload()
        ), handler, RequiresGameRestart.class);
  }
}

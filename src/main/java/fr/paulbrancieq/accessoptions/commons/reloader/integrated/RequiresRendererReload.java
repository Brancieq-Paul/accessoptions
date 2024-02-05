package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import net.minecraft.client.MinecraftClient;

public class RequiresRendererReload extends RequiresGameRestart {
  public RequiresRendererReload(OptionsAccessHandler handler) {
    super(handler);
    setRunnable(() -> MinecraftClient.getInstance().worldRenderer.reload());
  }
}

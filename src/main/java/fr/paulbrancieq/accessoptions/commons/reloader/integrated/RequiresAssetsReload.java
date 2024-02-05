package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import net.minecraft.client.MinecraftClient;

public class RequiresAssetsReload extends RequiresGameRestart {
    public RequiresAssetsReload(OptionsAccessHandler handler) {
      super(handler);
      setRunnable(() -> {
        MinecraftClient client = MinecraftClient.getInstance();
        client.setMipmapLevels(client.options.getMipmapLevels().getValue());
        client.reloadResourcesConcurrently();
      });
    }
}

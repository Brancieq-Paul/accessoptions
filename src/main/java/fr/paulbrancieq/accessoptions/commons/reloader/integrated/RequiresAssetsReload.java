package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import net.minecraft.client.MinecraftClient;

public class RequiresAssetsReload extends GenericReloader {
    public RequiresAssetsReload(OptionsAccessHandler handler) {
      super(() -> {
        MinecraftClient client = MinecraftClient.getInstance();
        client.setMipmapLevels(client.options.getMipmapLevels().getValue());
        client.reloadResourcesConcurrently();
      }, handler, RequiresGameRestart.class);
    }
}

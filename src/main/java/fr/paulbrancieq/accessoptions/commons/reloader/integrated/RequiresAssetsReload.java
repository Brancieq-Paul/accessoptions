package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import net.minecraft.client.MinecraftClient;

import java.util.List;

@SuppressWarnings("unused")
public class RequiresAssetsReload extends GenericReloader {
  protected RequiresAssetsReload(Builder builder) {
    super(builder);
  }

  @Override
  public List<Class<? extends Reloader>> getDirectParents() {
    return List.of(RequiresGameRestart.class);
  }

  public static Builder createBuilder() {
    return new Builder();
  }

  public static class Builder extends GenericReloader.Builder<Builder> {
    public Builder() {
      super();
      setRunnable((handler) -> {
        MinecraftClient client = MinecraftClient.getInstance();
        client.setMipmapLevels(client.options.getMipmapLevels().getValue());
        client.reloadResourcesConcurrently();
      });
    }

    @Override
    public RequiresAssetsReload build() {
      return new RequiresAssetsReload(this);
    }
  }
}

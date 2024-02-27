package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class RequiresRendererReload extends GenericReloader {
  protected RequiresRendererReload(Builder builder) {
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
      setRunnable((handler) ->
          MinecraftClient.getInstance().execute(
              () -> MinecraftClient.getInstance().worldRenderer.reload()
          ));
    }

    @Override
    public RequiresRendererReload build() {
      return new RequiresRendererReload(this);
    }
  }
}

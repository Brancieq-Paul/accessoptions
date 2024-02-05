package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererReload;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererUpdate;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.text.Text;

import java.io.IOException;

public class SodiumOptionsStorage extends OptionsStorageImpl<SodiumGameOptions> {
    public SodiumOptionsStorage(OptionsAccessHandler optionsAccessHandler) {
      super("sodium");
      this.registerOption("weather", OptionImpl.createBuilder(SodiumGameOptions.GraphicsQuality.class, this, "weather")
          .setName(Text.translatable("soundCategory.weather"))
          .setTooltip(Text.translatable("sodium.options.weather_quality.tooltip"))
          .setBinding((options, value) -> options.quality.weatherQuality = value, options -> options.quality.weatherQuality)
          .setValueFromString(SodiumGameOptions.GraphicsQuality::valueOf)
          .build());
      this.registerOption("leaves_quality", OptionImpl.createBuilder(SodiumGameOptions.GraphicsQuality.class, this, "leaves_quality")
          .setName(Text.translatable("sodium.options.leaves_quality.name"))
          .setTooltip(Text.translatable("sodium.options.leaves_quality.tooltip"))
          .setBinding((options, value) -> options.quality.leavesQuality = value, options -> options.quality.leavesQuality)
          .setValueFromString(SodiumGameOptions.GraphicsQuality::valueOf)
          .setReloaders(new RequiresRendererReload(optionsAccessHandler))
          .build());
      this.registerOption("vignette", OptionImpl.createBuilder(Boolean.class, this, "vignette")
          .setName(Text.translatable("sodium.options.vignette.name"))
          .setTooltip(Text.translatable("sodium.options.vignette.tooltip"))
          .setBinding((opts, value) -> opts.quality.enableVignette = value, opts -> opts.quality.enableVignette)
          .setValueFromString(Boolean::valueOf)
          .build());
      this.registerOption("chunk_update_threads", OptionImpl.createBuilder(Integer.class, this, "chunk_update_threads")
          .setName(Text.translatable("sodium.options.chunk_update_threads.name"))
          .setTooltip(Text.translatable("sodium.options.chunk_update_threads.tooltip"))
          .setBinding((opts, value) -> opts.performance.chunkBuilderThreads = value, opts -> opts.performance.chunkBuilderThreads)
          .setValueFromString(Integer::parseInt)
          .setReloaders(new RequiresRendererReload(optionsAccessHandler))
          .build());
      this.registerOption("always_defer_chunk_updates", OptionImpl.createBuilder(Boolean.class, this, "always_defer_chunk_updates")
          .setName(Text.translatable("sodium.options.always_defer_chunk_updates.name"))
          .setTooltip(Text.translatable("sodium.options.always_defer_chunk_updates.tooltip"))
          .setBinding((opts, value) -> opts.performance.alwaysDeferChunkUpdates = value, opts -> opts.performance.alwaysDeferChunkUpdates)
          .setValueFromString(Boolean::valueOf)
          .setReloaders(new RequiresRendererUpdate(optionsAccessHandler))
          .build());
      this.registerOption("use_block_face_culling", OptionImpl.createBuilder(Boolean.class, this, "use_block_face_culling")
          .setName(Text.translatable("sodium.options.use_block_face_culling.name"))
          .setTooltip(Text.translatable("sodium.options.use_block_face_culling.tooltip"))
          .setBinding((opts, value) -> opts.performance.useBlockFaceCulling = value, opts -> opts.performance.useBlockFaceCulling)
          .setValueFromString(Boolean::valueOf)
          .setReloaders(new RequiresRendererReload(optionsAccessHandler))
          .build());
      this.registerOption("use_fog_occlusion", OptionImpl.createBuilder(Boolean.class, this, "use_fog_occlusion")
          .setName(Text.translatable("sodium.options.use_fog_occlusion.name"))
          .setTooltip(Text.translatable("sodium.options.use_fog_occlusion.tooltip"))
          .setBinding((opts, value) -> opts.performance.useFogOcclusion = value, opts -> opts.performance.useFogOcclusion)
          .setValueFromString(Boolean::valueOf)
          .setReloaders(new RequiresRendererUpdate(optionsAccessHandler))
          .build());
      this.registerOption("use_entity_culling", OptionImpl.createBuilder(Boolean.class, this, "use_entity_culling")
          .setName(Text.translatable("sodium.options.use_entity_culling.name"))
          .setTooltip(Text.translatable("sodium.options.use_entity_culling.tooltip"))
          .setBinding((opts, value) -> opts.performance.useEntityCulling = value, opts -> opts.performance.useEntityCulling)
          .setValueFromString(Boolean::valueOf)
          .setReloaders(new RequiresRendererUpdate(optionsAccessHandler))
          .build());
      this.registerOption("animate_only_visible_textures", OptionImpl.createBuilder(Boolean.class, this, "animate_only_visible_textures")
          .setName(Text.translatable("sodium.options.animate_only_visible_textures.name"))
          .setTooltip(Text.translatable("sodium.options.animate_only_visible_textures.tooltip"))
          .setBinding((opts, value) -> opts.performance.animateOnlyVisibleTextures = value, opts -> opts.performance.animateOnlyVisibleTextures)
          .setValueFromString(Boolean::valueOf)
          .setReloaders(new RequiresRendererUpdate(optionsAccessHandler))
          .build());
      this.registerOption("use_no_error_context", OptionImpl.createBuilder(Boolean.class, this, "use_no_error_context")
          .setName(Text.translatable("sodium.options.use_no_error_context.name"))
          .setTooltip(Text.translatable("sodium.options.use_no_error_context.tooltip"))
          .setBinding((opts, value) -> opts.performance.useNoErrorGLContext = value, opts -> opts.performance.useNoErrorGLContext)
          .setValueFromString(Boolean::valueOf)
          .setReloaders(new RequiresRendererReload(optionsAccessHandler))
          .build());
      this.registerOption("use_persistent_mapping", OptionImpl.createBuilder(Boolean.class, this, "use_persistent_mapping")
          .setName(Text.translatable("sodium.options.use_persistent_mapping.name"))
          .setTooltip(Text.translatable("sodium.options.use_persistent_mapping.tooltip"))
          .setBinding((opts, value) -> opts.advanced.useAdvancedStagingBuffers = value, opts -> opts.advanced.useAdvancedStagingBuffers)
          .setValueFromString(Boolean::valueOf)
          .setReloaders(new RequiresRendererReload(optionsAccessHandler))
          .build());
      this.registerOption("cpu_render_ahead_limit", OptionImpl.createBuilder(Integer.class, this, "cpu_render_ahead_limit")
          .setName(Text.translatable("sodium.options.cpu_render_ahead_limit.name"))
          .setTooltip(Text.translatable("sodium.options.cpu_render_ahead_limit.tooltip"))
          .setBinding((opts, value) -> opts.advanced.cpuRenderAheadLimit = value, opts -> opts.advanced.cpuRenderAheadLimit)
          .setValueFromString(Integer::parseInt)
          .setReloaders(new RequiresRendererReload(optionsAccessHandler))
          .build());
    }

    @Override
    public SodiumGameOptions getData() {
        return SodiumClientMod.options();
    }

    @Override
    public void save() {
      try {
        SodiumGameOptions.writeToDisk(getData());
      } catch (IOException e) {
          AccessOptions.getLogger().error("Failed to save sodium options", e);
      }
    }
}

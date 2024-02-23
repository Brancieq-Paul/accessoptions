package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.typed.BooleanOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.EnumOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.RangedIntOption;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresGameRestart;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererReload;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererUpdate;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;

import java.io.IOException;
import java.util.LinkedHashMap;

public class SodiumOptionsStorage extends OptionsStorageImpl<SodiumGameOptions> {
  private static final LinkedHashMap<String, SodiumGameOptions.GraphicsQuality> graphicsQualityMap = new LinkedHashMap<>() {{
    put("options.gamma.default", SodiumGameOptions.GraphicsQuality.DEFAULT);
    put("options.clouds.fancy", SodiumGameOptions.GraphicsQuality.FANCY);
    put("options.clouds.fast", SodiumGameOptions.GraphicsQuality.FAST);
  }};

  public SodiumOptionsStorage(OptionsAccessHandler optionsAccessHandler) {
    super("sodium");
    this.registerOption("weather", EnumOption.createEnumBuilder(SodiumGameOptions.GraphicsQuality.class,
            this, "weather")
        .setDisplayName("soundCategory.weather")
        .setDescription("sodium.options.weather_quality.tooltip")
        .setBinding((options, value) -> options.quality.weatherQuality = value,
            options -> options.quality.weatherQuality)
        .addTranslatedAssociationMap(graphicsQualityMap)
        .build());
    this.registerOption("leaves_quality", EnumOption.createEnumBuilder(SodiumGameOptions.GraphicsQuality.class,
            this, "leaves_quality")
        .setDisplayName("sodium.options.leaves_quality.name")
        .setDescription("sodium.options.leaves_quality.tooltip")
        .setBinding((options, value) -> options.quality.leavesQuality = value, options -> options.quality.leavesQuality)
        .addTranslatedAssociationMap(graphicsQualityMap)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("vignette", BooleanOption.createBooleanBuilder(this, "vignette")
        .setDisplayName("sodium.options.vignette.name")
        .setDescription("sodium.options.vignette.tooltip")
        .setBinding((opts, value) -> opts.quality.enableVignette = value, opts -> opts.quality.enableVignette)
        .build());
    this.registerOption("chunk_update_threads", RangedIntOption.createRangedIntBuilder(this,
            "chunk_update_threads")
        .setDisplayName("sodium.options.chunk_update_threads.name")
        .setDescription("sodium.options.chunk_update_threads.tooltip")
        .setBinding((opts, value) -> opts.performance.chunkBuilderThreads = value, opts ->
            opts.performance.chunkBuilderThreads)
        .setRange(0, Runtime.getRuntime().availableProcessors())
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("always_defer_chunk_updates", BooleanOption.createBooleanBuilder(this,
            "always_defer_chunk_updates")
        .setDisplayName("sodium.options.always_defer_chunk_updates.name")
            .setDescription("sodium.options.always_defer_chunk_updates.tooltip")
        .setBinding((opts, value) -> opts.performance.alwaysDeferChunkUpdates = value, opts ->
            opts.performance.alwaysDeferChunkUpdates)
        .setReloaders(new RequiresRendererUpdate(optionsAccessHandler))
        .build());
    this.registerOption("use_block_face_culling", BooleanOption.createBooleanBuilder(this, "use_block_face_culling")
        .setDisplayName("sodium.options.use_block_face_culling.name")
        .setDescription("sodium.options.use_block_face_culling.tooltip")
        .setBinding((opts, value) -> opts.performance.useBlockFaceCulling = value, opts -> opts.performance.useBlockFaceCulling)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("use_fog_occlusion", BooleanOption.createBooleanBuilder(this, "use_fog_occlusion")
        .setDisplayName("sodium.options.use_fog_occlusion.name")
        .setDescription("sodium.options.use_fog_occlusion.tooltip")
        .setBinding((opts, value) -> opts.performance.useFogOcclusion = value, opts -> opts.performance.useFogOcclusion)
        .setReloaders(new RequiresRendererUpdate(optionsAccessHandler))
        .build());
    this.registerOption("use_entity_culling", BooleanOption.createBooleanBuilder(this, "use_entity_culling")
        .setDisplayName("sodium.options.use_entity_culling.name")
        .setDescription("sodium.options.use_entity_culling.tooltip")
        .setBinding((opts, value) -> opts.performance.useEntityCulling = value, opts -> opts.performance.useEntityCulling)
        .build());
    this.registerOption("animate_only_visible_textures", BooleanOption.createBooleanBuilder(this, "animate_only_visible_textures")
        .setDisplayName("sodium.options.animate_only_visible_textures.name")
        .setDescription("sodium.options.animate_only_visible_textures.tooltip")
        .setBinding((opts, value) -> opts.performance.animateOnlyVisibleTextures = value, opts -> opts.performance.animateOnlyVisibleTextures)
        .setReloaders(new RequiresRendererUpdate(optionsAccessHandler))
        .build());
    this.registerOption("use_no_error_context", BooleanOption.createBooleanBuilder(this, "use_no_error_context")
        .setDisplayName("sodium.options.use_no_error_context.name")
        .setDescription("sodium.options.use_no_error_context.tooltip")
        .setBinding((opts, value) -> opts.performance.useNoErrorGLContext = value, opts -> opts.performance.useNoErrorGLContext)
        .setReloaders(new RequiresGameRestart(optionsAccessHandler))
        .build());
    this.registerOption("use_persistent_mapping", BooleanOption.createBooleanBuilder(this, "use_persistent_mapping")
        .setDisplayName("sodium.options.use_persistent_mapping.name")
        .setDescription("sodium.options.use_persistent_mapping.tooltip")
        .setBinding((opts, value) -> opts.advanced.useAdvancedStagingBuffers = value, opts -> opts.advanced.useAdvancedStagingBuffers)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("cpu_render_ahead_limit", RangedIntOption.createRangedIntBuilder(this, "cpu_render_ahead_limit")
        .setDisplayName("sodium.options.cpu_render_ahead_limit.name")
        .setDescription("sodium.options.cpu_render_ahead_limit.tooltip")
        .setBinding((opts, value) -> opts.advanced.cpuRenderAheadLimit = value, opts -> opts.advanced.cpuRenderAheadLimit)
        .setRange(0, 9)
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

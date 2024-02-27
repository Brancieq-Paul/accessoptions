package fr.paulbrancieq.accessoptions.commons.storage.suppliers;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.options.typed.BooleanOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.EnumOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.RangedIntOption;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererReload;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererUpdate;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorageImpl;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SodiumStorageSupplier extends StorageSupplierImpl {
  private static final LinkedHashMap<String, SodiumGameOptions.GraphicsQuality> graphicsQualityMap = new LinkedHashMap<>() {{
    put("options.gamma.default", SodiumGameOptions.GraphicsQuality.DEFAULT);
    put("options.clouds.fancy", SodiumGameOptions.GraphicsQuality.FANCY);
    put("options.clouds.fast", SodiumGameOptions.GraphicsQuality.FAST);
  }};
  private final Map<String, OptionImpl.Builder<SodiumGameOptions, ?, ?>> optionsBuilders = new HashMap<>() {{
    put("weather", EnumOption.createEnumBuilder(SodiumGameOptions.GraphicsQuality.class, SodiumGameOptions.class, "weather")
        .setDisplayName("soundCategory.weather")
        .setDescription("sodium.options.weather_quality.tooltip")
        .setBinding((options, value) -> options.quality.weatherQuality = value,
            options -> options.quality.weatherQuality)
        .addTranslatedAssociationMap(graphicsQualityMap));
    put("leaves_quality", EnumOption.createEnumBuilder(SodiumGameOptions.GraphicsQuality.class, SodiumGameOptions.class, "leaves_quality")
        .setDisplayName("sodium.options.leaves_quality.name")
        .setDescription("sodium.options.leaves_quality.tooltip")
        .setBinding((options, value) -> options.quality.leavesQuality = value, options -> options.quality.leavesQuality)
        .addTranslatedAssociationMap(graphicsQualityMap)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("vignette", BooleanOption.createBooleanBuilder("vignette", SodiumGameOptions.class)
        .setDisplayName("sodium.options.vignette.name")
        .setDescription("sodium.options.vignette.tooltip")
        .setBinding((opts, value) -> opts.quality.enableVignette = value, opts -> opts.quality.enableVignette));
    put("chunk_update_threads", RangedIntOption.createRangedIntBuilder("chunk_update_threads", SodiumGameOptions.class)
        .setDisplayName("sodium.options.chunk_update_threads.name")
        .setDescription("sodium.options.chunk_update_threads.tooltip")
        .setBinding((opts, value) -> opts.performance.chunkBuilderThreads = value, opts ->
            opts.performance.chunkBuilderThreads)
        .setRange(0, Runtime.getRuntime().availableProcessors())
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("always_defer_chunk_updates", BooleanOption.createBooleanBuilder("always_defer_chunk_updates", SodiumGameOptions.class)
        .setDisplayName("sodium.options.always_defer_chunk_updates.name")
            .setDescription("sodium.options.always_defer_chunk_updates.tooltip")
        .setBinding((opts, value) -> opts.performance.alwaysDeferChunkUpdates = value, opts ->
            opts.performance.alwaysDeferChunkUpdates)
        .setReloaders(RequiresRendererUpdate.createBuilder()));
    put("use_block_face_culling", BooleanOption.createBooleanBuilder("use_block_face_culling", SodiumGameOptions.class)
        .setDisplayName("sodium.options.use_block_face_culling.name")
        .setDescription("sodium.options.use_block_face_culling.tooltip")
        .setBinding((opts, value) -> opts.performance.useBlockFaceCulling = value, opts -> opts.performance.useBlockFaceCulling)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("use_fog_occlusion", BooleanOption.createBooleanBuilder("use_fog_occlusion", SodiumGameOptions.class)
        .setDisplayName("sodium.options.use_fog_occlusion.name")
        .setDescription("sodium.options.use_fog_occlusion.tooltip")
        .setBinding((opts, value) -> opts.performance.useFogOcclusion = value, opts -> opts.performance.useFogOcclusion)
        .setReloaders(RequiresRendererUpdate.createBuilder()));
    put("use_entity_culling", BooleanOption.createBooleanBuilder("use_entity_culling", SodiumGameOptions.class)
        .setDisplayName("sodium.options.use_entity_culling.name")
        .setDescription("sodium.options.use_entity_culling.tooltip")
        .setBinding((opts, value) -> opts.performance.useEntityCulling = value, opts -> opts.performance.useEntityCulling)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("animate_only_visible_textures", BooleanOption.createBooleanBuilder("animate_only_visible_textures", SodiumGameOptions.class)
        .setDisplayName("sodium.options.animate_only_visible_textures.name")
        .setDescription("sodium.options.animate_only_visible_textures.tooltip")
        .setBinding((opts, value) -> opts.performance.animateOnlyVisibleTextures = value, opts -> opts.performance.animateOnlyVisibleTextures)
        .setReloaders(RequiresRendererUpdate.createBuilder()));
    put("use_no_error_context", BooleanOption.createBooleanBuilder("use_no_error_context", SodiumGameOptions.class)
        .setDisplayName("sodium.options.use_no_error_context.name")
        .setDescription("sodium.options.use_no_error_context.tooltip")
        .setBinding((opts, value) -> opts.performance.useNoErrorGLContext = value, opts -> opts.performance.useNoErrorGLContext)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("use_persistent_mapping", BooleanOption.createBooleanBuilder("use_persistent_mapping", SodiumGameOptions.class)
        .setDisplayName("sodium.options.use_persistent_mapping.name")
        .setDescription("sodium.options.use_persistent_mapping.tooltip")
        .setBinding((opts, value) -> opts.advanced.useAdvancedStagingBuffers = value, opts -> opts.advanced.useAdvancedStagingBuffers)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("cpu_render_ahead_limit", RangedIntOption.createRangedIntBuilder("cpu_render_ahead_limit", SodiumGameOptions.class)
        .setDisplayName("sodium.options.cpu_render_ahead_limit.name")
        .setDescription("sodium.options.cpu_render_ahead_limit.tooltip")
        .setBinding((opts, value) -> opts.advanced.cpuRenderAheadLimit = value, opts -> opts.advanced.cpuRenderAheadLimit)
        .setRange(0, 9));
  }};
  public SodiumStorageSupplier() {
    super(
        optionsAccessHandler -> new OptionsStorageImpl<>("sodium", SodiumClientMod::options, () -> {
          try {
            SodiumGameOptions.writeToDisk(SodiumClientMod.options());
          } catch (IOException e) {
            AccessOptions.getLogger().error("Failed to save sodium options", e);
          }
        }, new HashMap<>(), optionsAccessHandler), "sodium");
    this.storageSupplier = optionsAccessHandler -> new OptionsStorageImpl<>("sodium", SodiumClientMod::options, () -> {
      try {
        SodiumGameOptions.writeToDisk(SodiumClientMod.options());
      } catch (IOException e) {
        AccessOptions.getLogger().error("Failed to save sodium options", e);
      }
    }, optionsBuilders, optionsAccessHandler);
  }
}

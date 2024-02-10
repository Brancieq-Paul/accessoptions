package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererReload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.*;
import net.minecraft.client.render.ChunkBuilderMode;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.network.message.ChatVisibility;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.util.Arm;

import java.util.function.Function;

public class MinecraftOptionsStorage extends OptionsStorageImpl<GameOptions> {
  private final MinecraftClient client;

  private static final Function<String, NarratorMode> narratorModeStringConsumer = (value) -> switch (value) {
    case "all" -> NarratorMode.ALL;
    case "chat" -> NarratorMode.CHAT;
    case "off" -> NarratorMode.OFF;
    case "system" -> NarratorMode.SYSTEM;
    default -> throw new IllegalArgumentException("Invalid narrator mode value: " + value);
  };

  private static final Function<String, ParticlesMode> particlesModeStringConsumer = (value) -> switch (value) {
    case "all" -> ParticlesMode.ALL;
    case "decreased" -> ParticlesMode.DECREASED;
    case "minimal" -> ParticlesMode.MINIMAL;
    default -> throw new IllegalArgumentException("Invalid particles mode value: " + value);
  };

  private static final Function<String, GraphicsMode> graphicsModeStringConsumer = (value) -> switch (value) {
    case "fancy" -> GraphicsMode.FANCY;
    case "fast" -> GraphicsMode.FAST;
    case "fabulous" -> GraphicsMode.FABULOUS;
    default -> throw new IllegalArgumentException("Invalid graphics mode value: " + value);
  };

  private static final Function<String, ChunkBuilderMode> chunkBuilderModeStringConsumer = (value) -> switch (value) {
    case "none" -> ChunkBuilderMode.NONE;
    case "player" -> ChunkBuilderMode.PLAYER_AFFECTED;
    case "nearby" -> ChunkBuilderMode.NEARBY;
    default -> throw new IllegalArgumentException("Invalid chunk builder mode value: " + value);
  };

  private static final Function<String, CloudRenderMode> cloudRenderModeStringConsumer = (value) -> switch (value) {
    case "fast" -> CloudRenderMode.FAST;
    case "fancy" -> CloudRenderMode.FANCY;
    case "off" -> CloudRenderMode.OFF;
    default -> throw new IllegalArgumentException("Invalid cloud render mode value: " + value);
  };

  private static final Function<String, ChatVisibility> chatVisibilityStringConsumer = (value) -> switch (value) {
    case "full" -> ChatVisibility.FULL;
    case "system" -> ChatVisibility.SYSTEM;
    case "hidden" -> ChatVisibility.HIDDEN;
    default -> throw new IllegalArgumentException("Invalid chat visibility value: " + value);
  };

  private static final Function<String, Arm> armStringConsumer = (value) -> switch (value) {
    case "left" -> Arm.LEFT;
    case "right" -> Arm.RIGHT;
    default -> throw new IllegalArgumentException("Invalid arm value: " + value);
  };

  private static final Function<String, AttackIndicator> attackIndicatorModeStringConsumer = (value) -> switch (value) {
    case "off" -> AttackIndicator.OFF;
    case "crosshair" -> AttackIndicator.CROSSHAIR;
    case "hotbar" -> AttackIndicator.HOTBAR;
    default -> throw new IllegalArgumentException("Invalid attack indicator mode value: " + value);
  };

  private static final Function<String, TutorialStep> tutorialStepStringConsumer = (value) -> switch (value) {
    case "movement" -> TutorialStep.MOVEMENT;
    case "find_tree" -> TutorialStep.FIND_TREE;
    case "punch_tree" -> TutorialStep.PUNCH_TREE;
    case "open_inventory" -> TutorialStep.OPEN_INVENTORY;
    case "craft_planks" -> TutorialStep.CRAFT_PLANKS;
    case "none" -> TutorialStep.NONE;
    default -> throw new IllegalArgumentException("Invalid tutorial step value: " + value);
  };

  public MinecraftOptionsStorage(OptionsAccessHandler optionsAccessHandler) {
    super("minecraft");
    this.client = MinecraftClient.getInstance();
    this.registerOption("autoJump", OptionImpl.createBuilder(Boolean.class, this, "autoJump")
        .setName(Text.translatable("options.autoJump"))
        .setTooltip(Text.of("Automatically jump when running into a block"))
        .setBinding((options, value) -> options.getAutoJump().setValue(value),
            options -> options.getAutoJump().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("operatorItemsTab", OptionImpl.createBuilder(Boolean.class, this,
            "operatorItemsTab")
        .setName(Text.translatable("options.operatorItemsTab"))
        .setTooltip(Text.of("Show the operator items tab in the creative inventory"))
        .setBinding((options, value) -> options.getOperatorItemsTab().setValue(value),
            options -> options.getOperatorItemsTab().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("autoSuggestions", OptionImpl.createBuilder(Boolean.class, this,
            "autoSuggestions")
        .setName(Text.translatable("options.autoSuggestCommands"))
        .setTooltip(Text.of("Auto suggest commands"))
        .setBinding((options, value) -> options.getAutoSuggestions().setValue(value),
            options -> options.getAutoSuggestions().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("chatColors", OptionImpl.createBuilder(Boolean.class, this, "chatColors")
        .setName(Text.translatable("options.chat.color"))
        .setTooltip(Text.of("Enable chat colors"))
        .setBinding((options, value) -> options.getChatColors().setValue(value),
            options -> options.getChatColors().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("chatLinks", OptionImpl.createBuilder(Boolean.class, this, "chatLinks")
        .setName(Text.translatable("options.chat.links"))
        .setTooltip(Text.of("Enable chat links"))
        .setBinding((options, value) -> options.getChatLinks().setValue(value),
            options -> options.getChatLinks().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("chatLinksPrompt", OptionImpl.createBuilder(Boolean.class, this,
            "chatLinksPrompt")
        .setName(Text.translatable("options.chat.links.prompt"))
        .setTooltip(Text.of("Prompt for chat links"))
        .setBinding((options, value) -> options.getChatLinksPrompt().setValue(value),
            options -> options.getChatLinksPrompt().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("enableVsync", OptionImpl.createBuilder(Boolean.class, this,
            "enableVsync")
        .setName(Text.translatable("options.vsync"))
        .setTooltip(Text.of("Enable Vsync"))
        .setBinding((options, value) -> options.getEnableVsync().setValue(value),
            options -> options.getEnableVsync().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("options.entityShadows", OptionImpl.createBuilder(Boolean.class, this,
            "entityShadows")
        .setName(Text.translatable("options.entityShadows"))
        .setTooltip(Text.of("Entity shadows"))
        .setBinding((options, value) -> options.getEntityShadows().setValue(value),
            options -> options.getEntityShadows().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("forceUnicodeFont", OptionImpl.createBuilder(Boolean.class, this,
            "forceUnicodeFont")
        .setName(Text.translatable("options.forceUnicodeFont"))
        .setTooltip(Text.of("Force unicode font"))
        .setBinding((options, value) -> options.getForceUnicodeFont().setValue(value),
            options -> options.getForceUnicodeFont().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("invertYMouse", OptionImpl.createBuilder(Boolean.class, this,
        "invertYMouse")
        .setName(Text.translatable("options.invertMouse"))
        .setTooltip(Text.of("Invert mouse"))
        .setBinding((options, value) -> options.getInvertYMouse().setValue(value),
            options -> options.getInvertYMouse().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("discreteMouseScroll", OptionImpl.createBuilder(Boolean.class, this,
        "discreteMouseScroll")
        .setName(Text.translatable("options.discrete_mouse_scroll"))
        .setTooltip(Text.of("Discrete mouse scroll"))
        .setBinding((options, value) -> options.getDiscreteMouseScroll().setValue(value),
            options -> options.getDiscreteMouseScroll().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("realmsNotifications", OptionImpl.createBuilder(Boolean.class, this,
        "realmsNotifications")
        .setName(Text.translatable("options.realmsNotifications"))
        .setTooltip(Text.of("Realms notifications"))
        .setBinding((options, value) -> options.getRealmsNotifications().setValue(value),
            options -> options.getRealmsNotifications().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("reducedDebugInfo", OptionImpl.createBuilder(Boolean.class, this,
        "reducedDebugInfo")
        .setName(Text.translatable("options.reducedDebugInfo"))
        .setTooltip(Text.of("Reduced debug info"))
        .setBinding((options, value) -> options.getReducedDebugInfo().setValue(value),
            options -> options.getReducedDebugInfo().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("showSubtitles", OptionImpl.createBuilder(Boolean.class, this,
        "showSubtitles")
        .setName(Text.translatable("options.showSubtitles"))
        .setTooltip(Text.of("Show subtitles"))
        .setBinding((options, value) -> options.getShowSubtitles().setValue(value),
            options -> options.getShowSubtitles().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("directionalAudio", OptionImpl.createBuilder(Boolean.class, this,
        "directionalAudio")
        .setName(Text.translatable("options.directionalAudio"))
        .setTooltip(Text.of("Directional audio"))
        .setBinding((options, value) -> options.getDirectionalAudio().setValue(value),
            options -> options.getDirectionalAudio().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("touchscreen", OptionImpl.createBuilder(Boolean.class, this,
        "touchscreen")
        .setName(Text.translatable("options.touchscreen"))
        .setTooltip(Text.of("Touchscreen"))
        .setBinding((options, value) -> options.getTouchscreen().setValue(value),
            options -> options.getTouchscreen().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("fullscreen", OptionImpl.createBuilder(Boolean.class, this,
        "fullscreen")
        .setName(Text.translatable("options.fullscreen"))
        .setTooltip(Text.of("Fullscreen"))
        .setBinding((options, value) -> options.getFullscreen().setValue(value),
            options -> options.getFullscreen().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("bobView", OptionImpl.createBuilder(Boolean.class, this,
        "bobView")
        .setName(Text.translatable("options.viewBobbing"))
        .setTooltip(Text.of("Camera movement while walking"))
        .setBinding((options, value) -> options.getBobView().setValue(value),
            options -> options.getBobView().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("toggleCrouch", OptionImpl.createBuilder(Boolean.class, this,
        "toggleCrouch")
        .setName(Text.translatable("key.sneak"))
        .setTooltip(Text.of("Toggle crouch"))
        .setBinding((options, value) -> options.getSneakToggled().setValue(value),
            options -> options.getSneakToggled().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("toggleSprint", OptionImpl.createBuilder(Boolean.class, this,
        "toggleSprint")
        .setName(Text.translatable("key.sprint"))
        .setTooltip(Text.of("Toggle sprint"))
        .setBinding((options, value) -> options.getSprintToggled().setValue(value),
            options -> options.getSprintToggled().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("darkMojangStudiosBackground", OptionImpl.createBuilder(Boolean.class, this,
        "darkMojangStudiosBackground")
        .setName(Text.translatable("options.darkMojangStudiosBackgroundColor"))
        .setTooltip(Text.of("Dark Mojang Studios background"))
        .setBinding((options, value) -> options.getMonochromeLogo().setValue(value),
            options -> options.getMonochromeLogo().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("hideLightningFlashes", OptionImpl.createBuilder(Boolean.class, this,
        "hideLightningFlashes")
        .setName(Text.translatable("options.hideLightningFlashes"))
        .setTooltip(Text.of("Hide lightning flashes"))
        .setBinding((options, value) -> options.getHideMatchedNames().setValue(value),
            options -> options.getHideMatchedNames().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("hideSplashTexts", OptionImpl.createBuilder(Boolean.class, this,
        "hideSplashTexts")
        .setName(Text.translatable("options.hideSplashTexts"))
        .setTooltip(Text.of("Hide splash texts"))
        .setBinding((options, value) -> options.getHideMatchedNames().setValue(value),
            options -> options.getHideMatchedNames().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("mouseSensitivity", OptionImpl.createBuilder(Double.class, this,
        "mouseSensitivity")
        .setName(Text.translatable("options.sensitivity"))
        .setTooltip(Text.of("Mouse sensitivity"))
        .setBinding((options, value) -> options.getMouseSensitivity().setValue(value),
            options -> options.getMouseSensitivity().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("fov", OptionImpl.createBuilder(Integer.class, this,
        "fov")
        .setName(Text.translatable("options.fov"))
        .setTooltip(Text.of("Field of view"))
        .setBinding((options, value) -> options.getFov().setValue(value),
            options -> options.getFov().getValue())
        .setValueFromString(Integer::parseInt)
        .build());
    this.registerOption("screenEffectScale", OptionImpl.createBuilder(Double.class, this,
        "screenEffectScale")
        .setName(Text.translatable("options.screenEffectScale"))
        .setTooltip(Text.of("Screen distortion effect scale"))
        .setBinding((options, value) -> options.getDistortionEffectScale().setValue(value),
            options -> options.getDistortionEffectScale().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("fovEffectScale", OptionImpl.createBuilder(Double.class, this,
        "fovEffectScale")
        .setName(Text.translatable("options.fovEffectScale"))
        .setTooltip(Text.of("FOV effect scale"))
        .setBinding((options, value) -> options.getFovEffectScale().setValue(value),
            options -> options.getFovEffectScale().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("darknessEffectScale", OptionImpl.createBuilder(Double.class, this,
        "darknessEffectScale")
        .setName(Text.translatable("options.darknessEffectScale"))
        .setTooltip(Text.of("Darkness effect scale"))
        .setBinding((options, value) -> options.getDarknessEffectScale().setValue(value),
            options -> options.getDarknessEffectScale().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("glintSpeed", OptionImpl.createBuilder(Double.class, this,
        "glintSpeed")
        .setName(Text.translatable("options.glintSpeed"))
        .setTooltip(Text.of("Glint effect scale"))
        .setBinding((options, value) -> options.getGlintSpeed().setValue(value),
            options -> options.getGlintSpeed().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("glintStrength", OptionImpl.createBuilder(Double.class, this,
        "glintStrength")
        .setName(Text.translatable("options.glintStrength"))
        .setTooltip(Text.of("Glint effect strength"))
        .setBinding((options, value) -> options.getGlintStrength().setValue(value),
            options -> options.getGlintStrength().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("damageTiltStrength", OptionImpl.createBuilder(Double.class, this,
        "damageTiltStrength")
        .setName(Text.translatable("options.damageTiltStrength"))
        .setTooltip(Text.of("Damage tilt strength"))
        .setBinding((options, value) -> options.getDamageTiltStrength().setValue(value),
            options -> options.getDamageTiltStrength().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("narratorHotkey", OptionImpl.createBuilder(NarratorMode.class, this,
        "narratorHotkey")
        .setName(Text.translatable("options.narrator"))
        .setTooltip(Text.of("Narrator"))
        .setBinding((options, value) -> options.getNarrator().setValue(value),
            options -> options.getNarrator().getValue())
        .setValueFromString(narratorModeStringConsumer)
        .build());
    this.registerOption("gamma", OptionImpl.createBuilder(Double.class, this,
        "gamma")
        .setName(Text.translatable("options.gamma"))
        .setTooltip(Text.of("Brightness"))
        .setBinding((options, value) -> options.getGamma().setValue(value),
            options -> options.getGamma().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("renderDistance", OptionImpl.createBuilder(Integer.class, this,
        "renderDistance")
        .setName(Text.translatable("options.renderDistance"))
        .setTooltip(Text.of("Render distance"))
        .setBinding((options, value) -> options.getViewDistance().setValue(value),
            options -> options.getViewDistance().getValue())
        .setValueFromString(Integer::parseInt)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("simulationDistance", OptionImpl.createBuilder(Integer.class, this,
        "simulationDistance")
        .setName(Text.translatable("options.simulationDistance"))
        .setTooltip(Text.of("Simulation distance"))
        .setBinding((options, value) -> {
          options.getSimulationDistance().setValue(value);
          client.onResolutionChanged();
          }, options -> options.getSimulationDistance().getValue())
        .setValueFromString(Integer::parseInt)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("entityDistanceScaling", OptionImpl.createBuilder(Double.class, this,
        "entityDistanceScaling")
        .setName(Text.translatable("options.entityDistanceScaling"))
        .setTooltip(Text.of("Entity distance scaling"))
        .setBinding((options, value) -> options.getEntityDistanceScaling().setValue(value),
            options -> options.getEntityDistanceScaling().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("guiScale", OptionImpl.createBuilder(Integer.class, this,
        "guiScale")
        .setName(Text.translatable("options.guiScale"))
        .setTooltip(Text.of("GUI scale"))
        .setBinding((options, value) -> options.getGuiScale().setValue(value),
            options -> options.getGuiScale().getValue())
        .setValueFromString(Integer::parseInt)
        .build());
    this.registerOption("particles", OptionImpl.createBuilder(ParticlesMode.class, this,
        "particles")
        .setName(Text.translatable("options.particles"))
        .setTooltip(Text.of("Particles"))
        .setBinding((options, value) -> options.getParticles().setValue(value),
            options -> options.getParticles().getValue())
        .setValueFromString(particlesModeStringConsumer)
        .build());
    this.registerOption("maxFps", OptionImpl.createBuilder(Integer.class, this,
        "maxFps")
        .setName(Text.translatable("options.framerateLimit"))
        .setTooltip(Text.of("Max fps"))
        .setBinding((options, value) -> options.getMaxFps().setValue(value),
            options -> options.getMaxFps().getValue())
        .setValueFromString(Integer::parseInt)
        .build());
    this.registerOption("graphicsMode", OptionImpl.createBuilder(GraphicsMode.class, this,
        "graphicsMode")
        .setName(Text.translatable("options.graphics"))
        .setTooltip(Text.of("Graphics mode"))
        .setBinding((options, value) -> options.getGraphicsMode().setValue(value),
            options -> options.getGraphicsMode().getValue())
        .setValueFromString(graphicsModeStringConsumer)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("ao", OptionImpl.createBuilder(Boolean.class, this,
        "ao")
        .setName(Text.translatable("options.ao"))
        .setTooltip(Text.of("Ambient occlusion"))
        .setBinding((options, value) -> options.getAo().setValue(value),
            options -> options.getAo().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("prioritizeChunkUpdates", OptionImpl.createBuilder(ChunkBuilderMode.class, this,
        "prioritizeChunkUpdates")
        .setName(Text.translatable("options.prioritizeChunkUpdates"))
        .setTooltip(Text.of("Prioritize chunk updates"))
        .setBinding((options, value) -> options.getChunkBuilderMode().setValue(value),
            options -> options.getChunkBuilderMode().getValue())
        .setValueFromString(chunkBuilderModeStringConsumer)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("biomeBlendRadius", OptionImpl.createBuilder(Integer.class, this,
        "biomeBlendRadius")
        .setName(Text.translatable("options.biomeBlendRadius"))
        .setTooltip(Text.of("Biome blend radius"))
        .setBinding((options, value) -> options.getBiomeBlendRadius().setValue(value),
            options -> options.getBiomeBlendRadius().getValue())
        .setValueFromString(Integer::parseInt)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("renderClouds", OptionImpl.createBuilder(CloudRenderMode.class, this,
        "renderClouds")
        .setName(Text.translatable("options.renderClouds"))
        .setTooltip(Text.of("Render clouds"))
        .setBinding((options, value) -> options.getCloudRenderMode().setValue(value),
            options -> options.getCloudRenderMode().getValue())
        .setValueFromString(cloudRenderModeStringConsumer)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    // TODO Reload language properly
    this.registerOption("lang", OptionImpl.createBuilder(String.class, this,
        "lang")
        .setName(Text.translatable("options.language"))
        .setTooltip(Text.of("Language"))
        .setBinding((options, value) -> options.language = value,
            options -> options.language)
        .setValueFromString(String::toString)
        .build());
    this.registerOption("soundDevice", OptionImpl.createBuilder(String.class, this,
        "soundDevice")
        .setName(Text.translatable("options.audioDevice"))
        .setTooltip(Text.of("Sound device"))
        .setBinding((options, value) -> options.getSoundDevice().setValue(value),
            options -> options.getSoundDevice().getValue())
        .setValueFromString(String::toString)
        .build());
    this.registerOption("chatVisibility", OptionImpl.createBuilder(ChatVisibility.class, this,
        "chatVisibility")
        .setName(Text.translatable("options.chat.visibility"))
        .setTooltip(Text.of("Chat visibility"))
        .setBinding((options, value) -> options.getChatVisibility().setValue(value),
            options -> options.getChatVisibility().getValue())
        .setValueFromString(chatVisibilityStringConsumer)
        .build());
    this.registerOption("chatOpacity", OptionImpl.createBuilder(Double.class, this,
        "chatOpacity")
        .setName(Text.translatable("options.chat.opacity"))
        .setTooltip(Text.of("Chat opacity"))
        .setBinding((options, value) -> options.getChatOpacity().setValue(value),
            options -> options.getChatOpacity().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("chatLineSpacing", OptionImpl.createBuilder(Double.class, this,
        "chatLineSpacing")
        .setName(Text.translatable("options.chat.line_spacing"))
        .setTooltip(Text.of("Chat line spacing"))
        .setBinding((options, value) -> options.getChatLineSpacing().setValue(value),
            options -> options.getChatLineSpacing().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("textBackgroundOpacity", OptionImpl.createBuilder(Double.class, this,
        "textBackgroundOpacity")
        .setName(Text.translatable("options.accessibility.text_background_opacity"))
        .setTooltip(Text.of("Text background opacity"))
        .setBinding((options, value) -> options.getTextBackgroundOpacity().setValue(value),
            options -> options.getTextBackgroundOpacity().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("backgroundForChatOnly", OptionImpl.createBuilder(Boolean.class, this,
        "backgroundForChatOnly")
        .setName(Text.translatable("options.accessibility.text_background"))
        .setTooltip(Text.of("Background for chat only"))
        .setBinding((options, value) -> options.getBackgroundForChatOnly().setValue(value),
            options -> options.getBackgroundForChatOnly().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("advancedItemTooltips", OptionImpl.createBuilder(Boolean.class, this,
        "advancedItemTooltips")
        .setName(Text.of("Advanced item tooltips"))
        .setTooltip(Text.of("Advanced item tooltips"))
        .setBinding((options, value) -> options.advancedItemTooltips = value,
            options -> options.advancedItemTooltips)
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("pauseOnLostFocus", OptionImpl.createBuilder(Boolean.class, this,
        "pauseOnLostFocus")
        .setName(Text.of("Pause on lost focus"))
        .setTooltip(Text.of("Pause on lost focus"))
        .setBinding((options, value) -> options.pauseOnLostFocus = value,
            options -> options.pauseOnLostFocus)
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("chatHeightFocused", OptionImpl.createBuilder(Double.class, this,
        "chatHeightFocused")
        .setName(Text.of("Chat height focused"))
        .setTooltip(Text.of("Chat height focused"))
        .setBinding((options, value) -> options.getChatHeightFocused().setValue(value),
            options -> options.getChatHeightFocused().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("chatDelay", OptionImpl.createBuilder(Double.class, this,
        "chatDelay")
        .setName(Text.of("Chat delay"))
        .setTooltip(Text.of("Chat delay"))
        .setBinding((options, value) -> options.getChatDelay().setValue(value),
            options -> options.getChatDelay().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("chatHeightUnfocused", OptionImpl.createBuilder(Double.class, this,
        "chatHeightUnfocused")
        .setName(Text.of("Chat height unfocused"))
        .setTooltip(Text.of("Chat height unfocused"))
        .setBinding((options, value) -> options.getChatHeightUnfocused().setValue(value),
            options -> options.getChatHeightUnfocused().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("chatScale", OptionImpl.createBuilder(Double.class, this,
        "chatScale")
        .setName(Text.of("Chat scale"))
        .setTooltip(Text.of("Chat scale"))
        .setBinding((options, value) -> options.getChatScale().setValue(value),
            options -> options.getChatScale().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("chatWidth", OptionImpl.createBuilder(Double.class, this,
        "chatWidth")
        .setName(Text.of("Chat width"))
        .setTooltip(Text.of("Chat width"))
        .setBinding((options, value) -> options.getChatWidth().setValue(value),
            options -> options.getChatWidth().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("notificationDisplayTime", OptionImpl.createBuilder(Double.class, this,
        "notificationDisplayTime")
        .setName(Text.of("Notification display time"))
        .setTooltip(Text.of("Notification display time"))
        .setBinding((options, value) -> options.getNotificationDisplayTime().setValue(value),
            options -> options.getNotificationDisplayTime().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("mipmapLevels", OptionImpl.createBuilder(Integer.class, this,
        "mipmapLevels")
        .setName(Text.of("Mipmap levels"))
        .setTooltip(Text.of("Mipmap levels"))
        .setBinding((options, value) -> options.getMipmapLevels().setValue(value),
            options -> options.getMipmapLevels().getValue())
        .setValueFromString(Integer::parseInt)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("mainHand", OptionImpl.createBuilder(Arm.class, this,
        "mainHand")
        .setName(Text.of("Main hand"))
        .setTooltip(Text.of("Main hand"))
        .setBinding((options, value) -> options.getMainArm().setValue(value),
            options -> options.getMainArm().getValue())
        .setValueFromString(armStringConsumer)
        .build());
    this.registerOption("attackIndicator", OptionImpl.createBuilder(AttackIndicator.class, this,
        "attackIndicator")
        .setName(Text.of("Attack indicator"))
        .setTooltip(Text.of("Attack indicator"))
        .setBinding((options, value) -> options.getAttackIndicator().setValue(value),
            options -> options.getAttackIndicator().getValue())
        .setValueFromString(attackIndicatorModeStringConsumer)
        .build());
    this.registerOption("narrator", OptionImpl.createBuilder(NarratorMode.class, this,
        "narrator")
        .setName(Text.of("Narrator"))
        .setTooltip(Text.of("Narrator"))
        .setBinding((options, value) -> options.getNarrator().setValue(value),
            options -> options.getNarrator().getValue())
        .setValueFromString(narratorModeStringConsumer)
        .build());
    this.registerOption("tutorialStep", OptionImpl.createBuilder(TutorialStep.class, this,
        "tutorialStep")
        .setName(Text.of("Tutorial step"))
        .setTooltip(Text.of("Tutorial step"))
        .setBinding((options, value) -> options.tutorialStep = value,
            options -> options.tutorialStep)
        .setValueFromString(tutorialStepStringConsumer)
        .build());
    this.registerOption("mouseWheelSensitivity", OptionImpl.createBuilder(Double.class, this,
        "mouseWheelSensitivity")
        .setName(Text.of("Mouse wheel sensitivity"))
        .setTooltip(Text.of("Mouse wheel sensitivity"))
        .setBinding((options, value) -> options.getMouseWheelSensitivity().setValue(value),
            options -> options.getMouseWheelSensitivity().getValue())
        .setValueFromString(Double::parseDouble)
        .build());
    this.registerOption("rawMouseInput", OptionImpl.createBuilder(Boolean.class, this,
        "rawMouseInput")
        .setName(Text.of("Raw mouse input"))
        .setTooltip(Text.of("Raw mouse input"))
        .setBinding((options, value) -> options.getRawMouseInput().setValue(value),
            options -> options.getRawMouseInput().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("skipMultiplayerWarning", OptionImpl.createBuilder(Boolean.class, this,
        "skipMultiplayerWarning")
        .setName(Text.of("Skip multiplayer warning"))
        .setTooltip(Text.of("Skip multiplayer warning"))
        .setBinding((options, value) -> options.skipMultiplayerWarning = value,
            options -> options.skipMultiplayerWarning)
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("skipRealms32bitWarning", OptionImpl.createBuilder(Boolean.class, this,
        "skipRealms32bitWarning")
        .setName(Text.of("Skip realms 32-bit warning"))
        .setTooltip(Text.of("Skip realms 32-bit warning"))
        .setBinding((options, value) -> options.skipRealms32BitWarning = value,
            options -> options.skipRealms32BitWarning)
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("hideMatchedNames", OptionImpl.createBuilder(Boolean.class, this,
        "hideMatchedNames")
        .setName(Text.of("Hide matched names"))
        .setTooltip(Text.of("Hide matched names"))
        .setBinding((options, value) -> options.getHideMatchedNames().setValue(value),
            options -> options.getHideMatchedNames().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("hideBundleTutorial", OptionImpl.createBuilder(Boolean.class, this,
        "hideBundleTutorial")
        .setName(Text.of("Hide bundle tutorial"))
        .setTooltip(Text.of("Hide bundle tutorial"))
        .setBinding((options, value) -> options.hideBundleTutorial = value,
            options -> options.hideBundleTutorial)
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("syncChunkWrites", OptionImpl.createBuilder(Boolean.class, this,
        "syncChunkWrites")
        .setName(Text.of("Sync chunk writes"))
        .setTooltip(Text.of("Sync chunk writes"))
        .setBinding((options, value) -> options.syncChunkWrites = value,
            options -> options.syncChunkWrites)
        .setValueFromString(Boolean::parseBoolean)
        .build());
    this.registerOption("showAutosaveIndicator", OptionImpl.createBuilder(Boolean.class, this,
        "showAutosaveIndicator")
        .setName(Text.of("Show autosave indicator"))
        .setTooltip(Text.of("Show autosave indicator"))
        .setBinding((options, value) -> options.getShowAutosaveIndicator().setValue(value),
            options -> options.getShowAutosaveIndicator().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .build());
  }

  @Override
  @Environment(EnvType.CLIENT)
  public GameOptions getData() {
    return this.client.options;
  }

  @Override
  @Environment(EnvType.CLIENT)
  public void save() {
    this.getData().write();

    AccessOptions.getLogger().info("Saved minecraft options");
  }
}

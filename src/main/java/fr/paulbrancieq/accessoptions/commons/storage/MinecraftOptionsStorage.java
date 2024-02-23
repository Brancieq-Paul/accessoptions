package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.options.typed.BooleanOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.EnumOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.RangedDoubleOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.RangedIntOption;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererReload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.*;
import net.minecraft.client.render.ChunkBuilderMode;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.network.message.ChatVisibility;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

public class MinecraftOptionsStorage extends OptionsStorageImpl<GameOptions> {
  private final MinecraftClient client;

  public MinecraftOptionsStorage(OptionsAccessHandler optionsAccessHandler) {
    super("minecraft");
    this.client = MinecraftClient.getInstance();
    this.registerOption("autoJump", BooleanOption.createBooleanBuilder(this, "autoJump")
        .setDisplayName("options.autoJump")
        .setBinding((options, value) -> options.getAutoJump().setValue(value),
            options -> options.getAutoJump().getValue())
        .build());
    this.registerOption("operatorItemsTab", BooleanOption.createBooleanBuilder(this,
            "operatorItemsTab")
        .setDisplayName("options.operatorItemsTab")
        .setBinding((options, value) -> options.getOperatorItemsTab().setValue(value),
            options -> options.getOperatorItemsTab().getValue())
        .build());
    this.registerOption("autoSuggestions", BooleanOption.createBooleanBuilder(this,
            "autoSuggestions")
        .setDisplayName("options.autoSuggestCommands")
        .setBinding((options, value) -> options.getAutoSuggestions().setValue(value),
            options -> options.getAutoSuggestions().getValue())
        .build());
    this.registerOption("chatColors", BooleanOption.createBooleanBuilder(this, "chatColors")
        .setDisplayName("options.chat.color")
        .setBinding((options, value) -> options.getChatColors().setValue(value),
            options -> options.getChatColors().getValue())
        .build());
    this.registerOption("chatLinks", BooleanOption.createBooleanBuilder(this, "chatLinks")
        .setDisplayName("options.chat.links")
        .setBinding((options, value) -> options.getChatLinks().setValue(value),
            options -> options.getChatLinks().getValue())
        .build());
    this.registerOption("chatLinksPrompt", BooleanOption.createBooleanBuilder(this,
            "chatLinksPrompt")
        .setDisplayName("options.chat.links.prompt")
        .setBinding((options, value) -> options.getChatLinksPrompt().setValue(value),
            options -> options.getChatLinksPrompt().getValue())
        .build());
    this.registerOption("enableVsync", BooleanOption.createBooleanBuilder(this,
            "enableVsync")
        .setDisplayName("options.vsync")
        .setBinding((options, value) -> options.getEnableVsync().setValue(value),
            options -> options.getEnableVsync().getValue())
        .build());
    this.registerOption("options.entityShadows", BooleanOption.createBooleanBuilder(this,
            "entityShadows")
        .setDisplayName("options.entityShadows")
        .setBinding((options, value) -> options.getEntityShadows().setValue(value),
            options -> options.getEntityShadows().getValue())
        .build());
    this.registerOption("forceUnicodeFont", BooleanOption.createBooleanBuilder(this,
            "forceUnicodeFont")
        .setDisplayName("options.forceUnicodeFont")
        .setBinding((options, value) -> options.getForceUnicodeFont().setValue(value),
            options -> options.getForceUnicodeFont().getValue())
        .build());
    this.registerOption("invertYMouse", BooleanOption.createBooleanBuilder(this,
            "invertYMouse")
        .setDisplayName("options.invertMouse")
        .setBinding((options, value) -> options.getInvertYMouse().setValue(value),
            options -> options.getInvertYMouse().getValue())
        .build());
    this.registerOption("discreteMouseScroll", BooleanOption.createBooleanBuilder(this,
            "discreteMouseScroll")
        .setDisplayName("options.discrete_mouse_scroll")
        .setBinding((options, value) -> options.getDiscreteMouseScroll().setValue(value),
            options -> options.getDiscreteMouseScroll().getValue())
        .build());
    this.registerOption("realmsNotifications", BooleanOption.createBooleanBuilder(this,
            "realmsNotifications")
        .setDisplayName("options.realmsNotifications")
        .setBinding((options, value) -> options.getRealmsNotifications().setValue(value),
            options -> options.getRealmsNotifications().getValue())
        .build());
    this.registerOption("reducedDebugInfo", BooleanOption.createBooleanBuilder(this,
            "reducedDebugInfo")
        .setDisplayName("options.reducedDebugInfo")
        .setBinding((options, value) -> options.getReducedDebugInfo().setValue(value),
            options -> options.getReducedDebugInfo().getValue())
        .build());
    this.registerOption("showSubtitles", BooleanOption.createBooleanBuilder(this,
            "showSubtitles")
        .setDisplayName("options.showSubtitles")
        .setBinding((options, value) -> options.getShowSubtitles().setValue(value),
            options -> options.getShowSubtitles().getValue())
        .build());
    this.registerOption("directionalAudio", BooleanOption.createBooleanBuilder(this,
            "directionalAudio")
        .setDisplayName("options.directionalAudio")
        .setBinding((options, value) -> options.getDirectionalAudio().setValue(value),
            options -> options.getDirectionalAudio().getValue())
        .build());
    this.registerOption("touchscreen", BooleanOption.createBooleanBuilder(this,
            "touchscreen")
        .setDisplayName("options.touchscreen")
        .setBinding((options, value) -> options.getTouchscreen().setValue(value),
            options -> options.getTouchscreen().getValue())
        .build());
    this.registerOption("fullscreen", BooleanOption.createBooleanBuilder(this,
            "fullscreen")
        .setDisplayName("options.fullscreen")
        .setBinding((options, value) -> options.getFullscreen().setValue(value),
            options -> options.getFullscreen().getValue())
        .build());
    this.registerOption("bobView", BooleanOption.createBooleanBuilder(this,
            "bobView")
        .setDisplayName("options.viewBobbing")
        .setBinding((options, value) -> options.getBobView().setValue(value),
            options -> options.getBobView().getValue())
        .build());
    this.registerOption("toggleCrouch", BooleanOption.createBooleanBuilder(this,
            "toggleCrouch")
        .setDisplayName("key.sneak")
        .setBinding((options, value) -> options.getSneakToggled().setValue(value),
            options -> options.getSneakToggled().getValue())
        .build());
    this.registerOption("toggleSprint", BooleanOption.createBooleanBuilder(this,
            "toggleSprint")
        .setDisplayName("key.sprint")
        .setBinding((options, value) -> options.getSprintToggled().setValue(value),
            options -> options.getSprintToggled().getValue())
        .build());
    this.registerOption("darkMojangStudiosBackground", BooleanOption.createBooleanBuilder(this,
            "darkMojangStudiosBackground")
        .setDisplayName("options.darkMojangStudiosBackgroundColor")
        .setBinding((options, value) -> options.getMonochromeLogo().setValue(value),
            options -> options.getMonochromeLogo().getValue())
        .build());
    this.registerOption("hideLightningFlashes", BooleanOption.createBooleanBuilder(this,
            "hideLightningFlashes")
        .setDisplayName("options.hideLightningFlashes")
        .setDescription(Text.translatable("options.hideLightningFlashes.tooltip").getString())
        .setBinding((options, value) -> options.getHideMatchedNames().setValue(value),
            options -> options.getHideMatchedNames().getValue())
        .build());
    this.registerOption("hideSplashTexts", BooleanOption.createBooleanBuilder(this,
            "hideSplashTexts")
        .setDisplayName("options.hideSplashTexts")
        .setBinding((options, value) -> options.getHideMatchedNames().setValue(value),
            options -> options.getHideMatchedNames().getValue())
        .build());
    this.registerOption("mouseSensitivity", RangedDoubleOption.createRangedDoubleBuilder(this,
            "mouseSensitivity")
        .setDisplayName("options.sensitivity")
        .setBinding((options, value) -> options.getMouseSensitivity().setValue(value),
            options -> options.getMouseSensitivity().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("fov", RangedIntOption.createRangedIntBuilder(this,
            "fov")
        .setDisplayName("options.fov")
        .setBinding((options, value) -> options.getFov().setValue(value),
            options -> options.getFov().getValue())
        .setRange(30, 110)
        .build());
    this.registerOption("screenEffectScale", RangedDoubleOption.createRangedDoubleBuilder(this,
            "screenEffectScale")
        .setDisplayName("options.screenEffectScale")
        .setDescription("options.screenEffectScale.tooltip")
        .setBinding((options, value) -> options.getDistortionEffectScale().setValue(value),
            options -> options.getDistortionEffectScale().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("fovEffectScale", RangedDoubleOption.createRangedDoubleBuilder(this,
            "fovEffectScale")
        .setDisplayName("options.fovEffectScale")
        .setDescription("options.fovEffectScale.tooltip")
        .setBinding((options, value) -> options.getFovEffectScale().setValue(value),
            options -> options.getFovEffectScale().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("darknessEffectScale", RangedDoubleOption.createRangedDoubleBuilder(this,
            "darknessEffectScale")
        .setDisplayName("options.darknessEffectScale")
        .setDescription("options.darknessEffectScale.tooltip")
        .setBinding((options, value) -> options.getDarknessEffectScale().setValue(value),
            options -> options.getDarknessEffectScale().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("glintSpeed", RangedDoubleOption.createRangedDoubleBuilder(this,
            "glintSpeed")
        .setDisplayName("options.glintSpeed")
        .setDescription("options.glintSpeed.tooltip")
        .setBinding((options, value) -> options.getGlintSpeed().setValue(value),
            options -> options.getGlintSpeed().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("glintStrength", RangedDoubleOption.createRangedDoubleBuilder(this,
            "glintStrength")
        .setDisplayName("options.glintStrength")
        .setDescription("options.glintStrength.tooltip")
        .setBinding((options, value) -> options.getGlintStrength().setValue(value),
            options -> options.getGlintStrength().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("damageTiltStrength", RangedDoubleOption.createRangedDoubleBuilder(this,
            "damageTiltStrength")
        .setDisplayName("options.damageTiltStrength")
        .setDescription("options.damageTiltStrength.tooltip")
        .setBinding((options, value) -> options.getDamageTiltStrength().setValue(value),
            options -> options.getDamageTiltStrength().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("narratorHotkey", BooleanOption.createBooleanBuilder(this,
            "narratorHotkey")
        .setDisplayName("options.accessibility.narrator_hotkey")
        .setDescription(MinecraftClient.IS_SYSTEM_MAC ? Text.translatable(
            "options.accessibility.narrator_hotkey.mac.tooltip").getString() :
            Text.translatable("options.accessibility.narrator_hotkey.tooltip").getString())
        .setBinding((options, value) -> options.getNarratorHotkey().setValue(value),
            options -> options.getNarratorHotkey().getValue())
        .build());
    this.registerOption("gamma", RangedDoubleOption.createRangedDoubleBuilder(this,
            "gamma")
        .setDisplayName("options.gamma")
        .setBinding((options, value) -> options.getGamma().setValue(value),
            options -> options.getGamma().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("renderDistance", RangedIntOption.createRangedIntBuilder(this,
            "renderDistance")
        .setDisplayName("options.renderDistance")
        .setBinding((options, value) -> options.getViewDistance().setValue(value),
            options -> options.getViewDistance().getValue())
        .setRange(2, 32)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("simulationDistance", RangedIntOption.createRangedIntBuilder(this,
            "simulationDistance")
        .setDisplayName("options.simulationDistance")
        .setBinding((options, value) -> {
          options.getSimulationDistance().setValue(value);
          client.onResolutionChanged();
        }, options -> options.getSimulationDistance().getValue())
        .setRange(2, 32)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("entityDistanceScaling", RangedDoubleOption.createRangedDoubleBuilder(this,
            "entityDistanceScaling")
        .setDisplayName("options.entityDistanceScaling")
        .setBinding((options, value) -> options.getEntityDistanceScaling().setValue(value),
            options -> options.getEntityDistanceScaling().getValue())
        .setRange(0.5, 5.0)
        .build());
    this.registerOption("guiScale", RangedIntOption.createRangedIntBuilder(this,
            "guiScale")
        .setDisplayName("options.guiScale")
        .setBinding((options, value) -> {
              options.getGuiScale().setValue(value);
              client.onResolutionChanged();
            },
            options -> options.getGuiScale().getValue())
        .setRange(0, client.getWindow().calculateScaleFactor(0,
            client.forcesUnicodeFont()))
        .build());
    this.registerOption("particles", EnumOption.createEnumBuilder(ParticlesMode.class, this,
            "particles")
        .setDisplayName("options.particles")
        .setBinding((options, value) -> options.getParticles().setValue(value),
            options -> options.getParticles().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put(ParticlesMode.ALL.getTranslationKey(), ParticlesMode.ALL);
            this.put(ParticlesMode.DECREASED.getTranslationKey(), ParticlesMode.DECREASED);
            this.put(ParticlesMode.MINIMAL.getTranslationKey(), ParticlesMode.MINIMAL);
          }
        })
        .build());
    this.registerOption("maxFps", RangedIntOption.createRangedIntBuilder(this,
            "maxFps")
        .setDisplayName("options.framerateLimit")
        .setBinding((options, value) -> {
              options.getMaxFps().setValue(value);
              client.getWindow().setFramerateLimit(value);
            },
            options -> options.getMaxFps().getValue())
        .build());
    //TODO: setDescription with lambda
    this.registerOption("graphicsMode", EnumOption.createEnumBuilder(GraphicsMode.class, this,
            "graphicsMode")
        .setDisplayName("options.graphics")
        .setBinding((options, value) -> options.getGraphicsMode().setValue(value),
            options -> options.getGraphicsMode().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put(GraphicsMode.FAST.getTranslationKey(), GraphicsMode.FAST);
            this.put(GraphicsMode.FANCY.getTranslationKey(), GraphicsMode.FANCY);
            this.put(GraphicsMode.FABULOUS.getTranslationKey(), GraphicsMode.FABULOUS);
          }
        })
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    //TODO: setDescription with lambda
    this.registerOption("ao", BooleanOption.createBooleanBuilder(this,
            "ao")
        .setDisplayName("options.ao")
        .setBinding((options, value) -> options.getAo().setValue(value),
            options -> options.getAo().getValue())
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    //TODO: setDescription with lambda
    this.registerOption("prioritizeChunkUpdates", EnumOption.createEnumBuilder(ChunkBuilderMode.class, this,
            "prioritizeChunkUpdates")
        .setDisplayName("options.prioritizeChunkUpdates")
        .setBinding((options, value) -> options.getChunkBuilderMode().setValue(value),
            options -> options.getChunkBuilderMode().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put(ChunkBuilderMode.NONE.getTranslationKey(), ChunkBuilderMode.NONE);
            this.put(ChunkBuilderMode.PLAYER_AFFECTED.getTranslationKey(), ChunkBuilderMode.PLAYER_AFFECTED);
            this.put(ChunkBuilderMode.NEARBY.getTranslationKey(), ChunkBuilderMode.NEARBY);
          }
        })
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("biomeBlendRadius", RangedIntOption.createRangedIntBuilder(this,
            "biomeBlendRadius")
        .setDisplayName("options.biomeBlendRadius")
        .setBinding((options, value) -> options.getBiomeBlendRadius().setValue(value),
            options -> options.getBiomeBlendRadius().getValue())
        .setRange(0, 7)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("renderClouds", EnumOption.createEnumBuilder(CloudRenderMode.class, this,
            "renderClouds")
        .setDisplayName("options.renderClouds")
        .setBinding((options, value) -> options.getCloudRenderMode().setValue(value),
            options -> options.getCloudRenderMode().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put(CloudRenderMode.OFF.getTranslationKey(), CloudRenderMode.OFF);
            this.put(CloudRenderMode.FAST.getTranslationKey(), CloudRenderMode.FAST);
            this.put(CloudRenderMode.FANCY.getTranslationKey(), CloudRenderMode.FANCY);
          }
        })
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    // TODO Reload language properly
    this.registerOption("lang", OptionImpl.createBuilder(String.class, this,
            "lang")
        .setDisplayName("options.language")
        .setBinding((options, value) -> options.language = value,
            options -> options.language)
        .build());
    List<String> soundDeviceList = Stream.concat(Stream.of(""), MinecraftClient.getInstance().getSoundManager().getSoundDevices().stream()).toList();
    this.registerOption("soundDevice", EnumOption.createEnumBuilder(String.class, this,
            "soundDevice")
        .setDisplayName("options.audioDevice")
        .setBinding((options, value) -> options.getSoundDevice().setValue(value),
            options -> options.getSoundDevice().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            for (String soundDevice : soundDeviceList) {
              this.put(soundDevice, soundDevice);
            }
          }
        })
        .build());
    this.registerOption("chatVisibility", EnumOption.createEnumBuilder(ChatVisibility.class, this,
            "chatVisibility")
        .setDisplayName("options.chat.visibility")
        .setBinding((options, value) -> options.getChatVisibility().setValue(value),
            options -> options.getChatVisibility().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put(ChatVisibility.FULL.getTranslationKey(), ChatVisibility.FULL);
            this.put(ChatVisibility.SYSTEM.getTranslationKey(), ChatVisibility.SYSTEM);
            this.put(ChatVisibility.HIDDEN.getTranslationKey(), ChatVisibility.HIDDEN);
          }
        })
        .build());
    this.registerOption("chatOpacity", RangedDoubleOption.createRangedDoubleBuilder(this,
            "chatOpacity")
        .setDisplayName("options.chat.opacity")
        .setBinding((options, value) -> options.getChatOpacity().setValue(value),
            options -> options.getChatOpacity().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("chatLineSpacing", RangedDoubleOption.createRangedDoubleBuilder(this,
            "chatLineSpacing")
        .setDisplayName("options.chat.line_spacing")
        .setBinding((options, value) -> options.getChatLineSpacing().setValue(value),
            options -> options.getChatLineSpacing().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("textBackgroundOpacity", RangedDoubleOption.createRangedDoubleBuilder(this,
            "textBackgroundOpacity")
        .setDisplayName("options.accessibility.text_background_opacity")
        .setBinding((options, value) -> options.getTextBackgroundOpacity().setValue(value),
            options -> options.getTextBackgroundOpacity().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("backgroundForChatOnly", BooleanOption.createBooleanBuilder(this,
            "backgroundForChatOnly")
        .setDisplayName("options.accessibility.text_background")
        .setBinding((options, value) -> options.getBackgroundForChatOnly().setValue(value),
            options -> options.getBackgroundForChatOnly().getValue())
        .build());
    this.registerOption("advancedItemTooltips", BooleanOption.createBooleanBuilder(this,
            "advancedItemTooltips")
        .setDisplayName("access_options.minecraft.options.advanced_tooltips")
        .setDescription("access_options.minecraft.options.advanced_tooltips.description")
        .setBinding((options, value) -> options.advancedItemTooltips = value,
            options -> options.advancedItemTooltips)
        .build());
    this.registerOption("pauseOnLostFocus", BooleanOption.createBooleanBuilder(this,
            "pauseOnLostFocus")
        .setDisplayName("access_options.minecraft.options.pause_on_lost_focus")
        .setDescription("access_options.minecraft.options.pause_on_lost_focus.description")
        .setBinding((options, value) -> options.pauseOnLostFocus = value,
            options -> options.pauseOnLostFocus)
        .build());
    this.registerOption("chatHeightFocused", RangedDoubleOption.createRangedDoubleBuilder(this,
            "chatHeightFocused")
        .setDisplayName("options.chat.height.focused")
        .setBinding((options, value) -> options.getChatHeightFocused().setValue(value),
            options -> options.getChatHeightFocused().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("chatDelay", RangedDoubleOption.createRangedDoubleBuilder(this,
            "chatDelay")
        .setDisplayName("options.chat.delay_instant")
        .setBinding((options, value) -> options.getChatDelay().setValue(value),
            options -> options.getChatDelay().getValue())
        .setRange(0.0, 6.0)
        .build());
    this.registerOption("chatHeightUnfocused", RangedDoubleOption.createRangedDoubleBuilder(this,
            "chatHeightUnfocused")
        .setDisplayName("options.chat.height.unfocused")
        .setBinding((options, value) -> options.getChatHeightUnfocused().setValue(value),
            options -> options.getChatHeightUnfocused().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("chatScale", RangedDoubleOption.createRangedDoubleBuilder(this,
            "chatScale")
        .setDisplayName("options.chat.scale")
        .setBinding((options, value) -> options.getChatScale().setValue(value),
            options -> options.getChatScale().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("chatWidth", RangedDoubleOption.createRangedDoubleBuilder(this,
            "chatWidth")
        .setDisplayName("options.chat.width")
        .setBinding((options, value) -> options.getChatWidth().setValue(value),
            options -> options.getChatWidth().getValue())
        .setRange(0.0, 1.0)
        .build());
    this.registerOption("notificationDisplayTime", RangedDoubleOption.createRangedDoubleBuilder(this,
            "notificationDisplayTime")
        .setDisplayName("options.notifications.display_time")
        .setBinding((options, value) -> options.getNotificationDisplayTime().setValue(value),
            options -> options.getNotificationDisplayTime().getValue())
        .setRange(0.5, 10.0)
        .build());
    this.registerOption("mipmapLevels", RangedIntOption.createRangedIntBuilder(this,
            "mipmapLevels")
        .setDisplayName("options.mipmapLevels")
        .setBinding((options, value) -> options.getMipmapLevels().setValue(value),
            options -> options.getMipmapLevels().getValue())
        .setRange(0, 4)
        .setReloaders(new RequiresRendererReload(optionsAccessHandler))
        .build());
    this.registerOption("mainHand", EnumOption.createEnumBuilder(Arm.class, this,
            "mainHand")
        .setDisplayName("options.mainHand")
        .setBinding((options, value) -> options.getMainArm().setValue(value),
            options -> options.getMainArm().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put(Arm.LEFT.getTranslationKey(), Arm.LEFT);
            this.put(Arm.RIGHT.getTranslationKey(), Arm.RIGHT);
          }
        })
        .build());
    this.registerOption("attackIndicator", EnumOption.createEnumBuilder(AttackIndicator.class, this,
            "attackIndicator")
        .setDisplayName("options.attackIndicator")
        .setBinding((options, value) -> options.getAttackIndicator().setValue(value),
            options -> options.getAttackIndicator().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put(AttackIndicator.OFF.getTranslationKey(), AttackIndicator.OFF);
            this.put(AttackIndicator.CROSSHAIR.getTranslationKey(), AttackIndicator.CROSSHAIR);
            this.put(AttackIndicator.HOTBAR.getTranslationKey(), AttackIndicator.HOTBAR);
          }
        })
        .build());
    this.registerOption("narrator", EnumOption.createEnumBuilder(NarratorMode.class, this,
            "narrator")
        .setDisplayName("options.narrator")
        .setBinding((options, value) -> options.getNarrator().setValue(value),
            options -> options.getNarrator().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            this.put("options.narrator.off", NarratorMode.OFF);
            this.put("options.narrator.all", NarratorMode.ALL);
            this.put("options.narrator.chat", NarratorMode.CHAT);
            this.put("options.narrator.system", NarratorMode.SYSTEM);
          }
        })
        .build());
    this.registerOption("tutorialStep", EnumOption.createEnumBuilder(TutorialStep.class, this,
            "tutorialStep")
        .setDisplayName("access_options.minecraft.options.tutorial_step")
        .setBinding((options, value) -> options.tutorialStep = value,
            options -> options.tutorialStep)
        .addAssociationMap(new LinkedHashMap<>() {
          {
            this.put("movement", TutorialStep.MOVEMENT);
            this.put("find_tree", TutorialStep.FIND_TREE);
            this.put("punch_tree", TutorialStep.PUNCH_TREE);
            this.put("open_inventory", TutorialStep.OPEN_INVENTORY);
            this.put("craft_planks", TutorialStep.CRAFT_PLANKS);
            this.put("none", TutorialStep.NONE);
          }
        })
        .build());
    this.registerOption("mouseWheelSensitivity", RangedDoubleOption.createRangedDoubleBuilder(this,
            "mouseWheelSensitivity")
        .setDisplayName("options.mouseWheelSensitivity")
        .setBinding((options, value) -> options.getMouseWheelSensitivity().setValue(value),
            options -> options.getMouseWheelSensitivity().getValue())
        .setRange(1.0, 10.0)
        .build());
    this.registerOption("rawMouseInput", BooleanOption.createBooleanBuilder(this,
            "rawMouseInput")
        .setDisplayName("options.rawMouseInput")
        .setBinding((options, value) -> options.getRawMouseInput().setValue(value),
            options -> options.getRawMouseInput().getValue())
        .build());
    this.registerOption("skipMultiplayerWarning", BooleanOption.createBooleanBuilder(this,
            "skipMultiplayerWarning")
        .setDisplayName("access_options.minecraft.options.skip_multiplayer_warning")
        .setBinding((options, value) -> options.skipMultiplayerWarning = value,
            options -> options.skipMultiplayerWarning)
        .build());
    this.registerOption("skipRealms32bitWarning", BooleanOption.createBooleanBuilder(this,
            "skipRealms32bitWarning")
        .setDisplayName("access_options.minecraft.options.skip_realms_32_bit_warning")
        .setBinding((options, value) -> options.skipRealms32BitWarning = value,
            options -> options.skipRealms32BitWarning)
        .build());
    this.registerOption("hideMatchedNames", BooleanOption.createBooleanBuilder(this,
            "hideMatchedNames")
        .setDisplayName("options.hideMatchedNames")
        .setDescription("options.hideMatchedNames.tooltip")
        .setBinding((options, value) -> options.getHideMatchedNames().setValue(value),
            options -> options.getHideMatchedNames().getValue())
        .build());
    this.registerOption("hideBundleTutorial", BooleanOption.createBooleanBuilder(this,
            "hideBundleTutorial")
        .setDisplayName("access_options.minecraft.options.hide_bundle_tutorial")
        .setBinding((options, value) -> options.hideBundleTutorial = value,
            options -> options.hideBundleTutorial)
        .build());
    this.registerOption("showAutosaveIndicator", BooleanOption.createBooleanBuilder(this,
            "showAutosaveIndicator")
        .setDisplayName("access_options.minecraft.options.show_auto_save_indicator")
        .setBinding((options, value) -> options.getShowAutosaveIndicator().setValue(value),
            options -> options.getShowAutosaveIndicator().getValue())
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

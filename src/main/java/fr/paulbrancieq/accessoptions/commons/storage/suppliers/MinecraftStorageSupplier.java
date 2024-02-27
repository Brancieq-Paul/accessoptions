package fr.paulbrancieq.accessoptions.commons.storage.suppliers;

import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import fr.paulbrancieq.accessoptions.commons.options.typed.BooleanOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.EnumOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.RangedDoubleOption;
import fr.paulbrancieq.accessoptions.commons.options.typed.RangedIntOption;
import fr.paulbrancieq.accessoptions.commons.reloader.integrated.RequiresRendererReload;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorageImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.*;
import net.minecraft.client.render.ChunkBuilderMode;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.network.message.ChatVisibility;
import net.minecraft.util.Arm;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

public class MinecraftStorageSupplier extends StorageSupplierImpl {
  private final Map<String, OptionImpl.Builder<GameOptions, ?, ?>> optionsBuilders = new HashMap<>() {{
    put("autoJump", BooleanOption.createBooleanBuilder("autoJump", GameOptions.class)
        .setDisplayName("options.autoJump")
        .setBinding((options, value) -> options.getAutoJump().setValue(value),
            options -> options.getAutoJump().getValue()));
    put("operatorItemsTab", BooleanOption.createBooleanBuilder("operatorItemsTab", GameOptions.class)
        .setDisplayName("options.operatorItemsTab")
        .setBinding((options, value) -> options.getOperatorItemsTab().setValue(value),
            options -> options.getOperatorItemsTab().getValue()));
    put("autoSuggestions", BooleanOption.createBooleanBuilder("autoSuggestions", GameOptions.class)
        .setDisplayName("options.autoSuggestCommands")
        .setBinding((options, value) -> options.getAutoSuggestions().setValue(value),
            options -> options.getAutoSuggestions().getValue()));
    put("chatColors", BooleanOption.createBooleanBuilder("chatColors", GameOptions.class)
        .setDisplayName("options.chat.color")
        .setBinding((options, value) -> options.getChatColors().setValue(value),
            options -> options.getChatColors().getValue()));
    put("chatLinks", BooleanOption.createBooleanBuilder("chatLinks", GameOptions.class)
        .setDisplayName("options.chat.link")
        .setBinding((options, value) -> options.getChatLinks().setValue(value),
            options -> options.getChatLinks().getValue()));
    put("chatLinksPrompt", BooleanOption.createBooleanBuilder("chatLinksPrompt", GameOptions.class)
        .setDisplayName("options.chat.link.prompt")
        .setBinding((options, value) -> options.getChatLinksPrompt().setValue(value),
            options -> options.getChatLinksPrompt().getValue()));
    put("enableVsync", BooleanOption.createBooleanBuilder("enableVsync", GameOptions.class)
        .setDisplayName("options.vsync")
        .setBinding((options, value) -> options.getEnableVsync().setValue(value),
            options -> options.getEnableVsync().getValue()));
    put("entityShadows", BooleanOption.createBooleanBuilder("entityShadows", GameOptions.class)
        .setDisplayName("options.entityShadows")
        .setBinding((options, value) -> options.getEntityShadows().setValue(value),
            options -> options.getEntityShadows().getValue()));
    put("forceUnicodeFont", BooleanOption.createBooleanBuilder("forceUnicodeFont", GameOptions.class)
        .setDisplayName("options.forceUnicodeFont")
        .setBinding((options, value) -> options.getForceUnicodeFont().setValue(value),
            options -> options.getForceUnicodeFont().getValue()));
    put("invertYMouse", BooleanOption.createBooleanBuilder("invertYMouse", GameOptions.class)
        .setDisplayName("options.invertYMouse")
        .setBinding((options, value) -> options.getInvertYMouse().setValue(value),
            options -> options.getInvertYMouse().getValue()));
    put("discreteMouseScroll", BooleanOption.createBooleanBuilder("discreteMouseScroll", GameOptions.class)
        .setDisplayName("options.discrete_mouse_scroll")
        .setBinding((options, value) -> options.getDiscreteMouseScroll().setValue(value),
            options -> options.getDiscreteMouseScroll().getValue()));
    put("realmsNotifications", BooleanOption.createBooleanBuilder("realmsNotifications", GameOptions.class)
        .setDisplayName("options.realmsNotifications")
        .setBinding((options, value) -> options.getRealmsNotifications().setValue(value),
            options -> options.getRealmsNotifications().getValue()));
    put("reducedDebugInfo", BooleanOption.createBooleanBuilder("reducedDebugInfo", GameOptions.class)
        .setDisplayName("options.reducedDebugInfo")
        .setBinding((options, value) -> options.getReducedDebugInfo().setValue(value),
            options -> options.getReducedDebugInfo().getValue()));
    put("showSubtitles", BooleanOption.createBooleanBuilder("showSubtitles", GameOptions.class)
        .setDisplayName("options.showSubtitles")
        .setBinding((options, value) -> options.getShowSubtitles().setValue(value),
            options -> options.getShowSubtitles().getValue()));
    put("directionalAudio", BooleanOption.createBooleanBuilder("directionalAudio", GameOptions.class)
        .setDisplayName("options.directionalAudio")
        .setBinding((options, value) -> options.getDirectionalAudio().setValue(value),
            options -> options.getDirectionalAudio().getValue()));
    put("touchscreen", BooleanOption.createBooleanBuilder("touchscreen", GameOptions.class)
        .setDisplayName("options.touchscreen")
        .setBinding((options, value) -> options.getTouchscreen().setValue(value),
            options -> options.getTouchscreen().getValue()));
    put("fullscreen", BooleanOption.createBooleanBuilder("fullscreen", GameOptions.class)
        .setDisplayName("options.fullscreen")
        .setBinding((options, value) -> options.getFullscreen().setValue(value),
            options -> options.getFullscreen().getValue()));
    put("bobView", BooleanOption.createBooleanBuilder("bobView", GameOptions.class)
        .setDisplayName("options.viewBobbing")
        .setBinding((options, value) -> options.getBobView().setValue(value),
            options -> options.getBobView().getValue()));
    put("toggleCrouch", BooleanOption.createBooleanBuilder("toggleCrouch", GameOptions.class)
        .setDisplayName("key.sneak")
        .setBinding((options, value) -> options.getSneakToggled().setValue(value),
            options -> options.getSneakToggled().getValue()));
    put("toggleSprint", BooleanOption.createBooleanBuilder("toggleSprint", GameOptions.class)
        .setDisplayName("key.sprint")
        .setBinding((options, value) -> options.getSprintToggled().setValue(value),
            options -> options.getSprintToggled().getValue()));
    put("darkMojangStudiosBackground", BooleanOption.createBooleanBuilder("darkMojangStudiosBackground",
            GameOptions.class)
        .setDisplayName("options.mojangStudiosBackground")
        .setBinding((options, value) -> options.getMonochromeLogo().setValue(value),
            options -> options.getMonochromeLogo().getValue()));
    put("hideLightningFlashes", BooleanOption.createBooleanBuilder("hideLightningFlashes", GameOptions.class)
        .setDisplayName("options.hideLightningFlashes")
        .setDescription("options.hideLightningFlashes.tooltip")
        .setBinding((options, value) -> options.getHideLightningFlashes().setValue(value),
            options -> options.getHideLightningFlashes().getValue()));
    put("hideSplashTexts", BooleanOption.createBooleanBuilder("hideSplashTexts", GameOptions.class)
        .setDisplayName("options.hideSplash")
        .setBinding((options, value) -> options.getHideSplashTexts().setValue(value),
            options -> options.getHideSplashTexts().getValue()));
    put("mouseSensitivity", RangedDoubleOption.createRangedDoubleBuilder("mouseSensitivity", GameOptions.class)
        .setDisplayName("options.sensitivity")
        .setBinding((options, value) -> options.getMouseSensitivity().setValue(value),
            options -> options.getMouseSensitivity().getValue())
        .setRange(0.0, 1.0));
    put("fov", RangedIntOption.createRangedIntBuilder("fov", GameOptions.class)
        .setDisplayName("options.fov")
        .setBinding((options, value) -> options.getFov().setValue(value),
            options -> options.getFov().getValue())
        .setRange(30, 110));
    put("screenEffectScale", RangedDoubleOption.createRangedDoubleBuilder("screenEffectScale",
            GameOptions.class)
        .setDisplayName("options.screenEffectScale")
        .setDescription("options.screenEffectScale.tooltip")
        .setBinding((options, value) -> options.getDistortionEffectScale().setValue(value),
            options -> options.getDistortionEffectScale().getValue())
        .setRange(0.0, 1.0));
    put("fovEffectScale", RangedDoubleOption.createRangedDoubleBuilder("fovEffectScale", GameOptions.class)
        .setDisplayName("options.fovEffectScale")
        .setDescription("options.fovEffectScale.tooltip")
        .setBinding((options, value) -> options.getFovEffectScale().setValue(value),
            options -> options.getFovEffectScale().getValue())
        .setRange(0.0, 1.0));
    put("darknessEffectScale", RangedDoubleOption.createRangedDoubleBuilder("darknessEffectScale",
            GameOptions.class)
        .setDisplayName("options.darknessEffectScale")
        .setDescription("options.darknessEffectScale.tooltip")
        .setBinding((options, value) -> options.getDarknessEffectScale().setValue(value),
            options -> options.getDarknessEffectScale().getValue())
        .setRange(0.0, 1.0));
    put("glintSpeed", RangedDoubleOption.createRangedDoubleBuilder("glintSpeed", GameOptions.class)
        .setDisplayName("options.glintSpeed")
        .setDescription("options.glintSpeed.tooltip")
        .setBinding((options, value) -> options.getGlintSpeed().setValue(value),
            options -> options.getGlintSpeed().getValue())
        .setRange(0.0, 1.0));
    put("glintStrength", RangedDoubleOption.createRangedDoubleBuilder("glintStrength", GameOptions.class)
        .setDisplayName("options.glintStrength")
        .setDescription("options.glintStrength.tooltip")
        .setBinding((options, value) -> options.getGlintStrength().setValue(value),
            options -> options.getGlintStrength().getValue())
        .setRange(0.0, 1.0));
    put("damageTiltStrength", RangedDoubleOption.createRangedDoubleBuilder("damageTiltStrength",
            GameOptions.class)
        .setDisplayName("options.damageTiltStrength")
        .setDescription("options.damageTiltStrength.tooltip")
        .setBinding((options, value) -> options.getDamageTiltStrength().setValue(value),
            options -> options.getDamageTiltStrength().getValue())
        .setRange(0.0, 1.0));
    put("narratorHotkey", BooleanOption.createBooleanBuilder("narratorHotkey", GameOptions.class)
        .setDisplayName("options.accessibility.narrator_hotkey")
        .setDescription(MinecraftClient.IS_SYSTEM_MAC ? "options.accessibility.narrator_hotkey.mac.tooltip" :
            "options.accessibility.narrator_hotkey.tooltip")
        .setBinding((options, value) -> options.getNarratorHotkey().setValue(value),
            options -> options.getNarratorHotkey().getValue()));
    put("gamma", RangedDoubleOption.createRangedDoubleBuilder("gamma", GameOptions.class)
        .setDisplayName("options.gamma")
        .setBinding((options, value) -> options.getGamma().setValue(value),
            options -> options.getGamma().getValue())
        .setRange(0.0, 1.0));
    put("renderDistance", RangedIntOption.createRangedIntBuilder("renderDistance", GameOptions.class)
        .setDisplayName("options.renderDistance")
        .setBinding((options, value) -> options.getViewDistance().setValue(value),
            options -> options.getViewDistance().getValue())
        .setRange(2, 32)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("simulationDistance", RangedIntOption.createRangedIntBuilder("simulationDistance", GameOptions.class)
        .setDisplayName("options.simulationDistance")
        .setBinding((options, value) -> {
          options.getSimulationDistance().setValue(value);
          MinecraftClient.getInstance().onResolutionChanged();
        }, options -> options.getSimulationDistance().getValue())
        .setRange(2, 32)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("entityDistanceScaling", RangedDoubleOption.createRangedDoubleBuilder("entityDistanceScaling",
            GameOptions.class)
        .setDisplayName("options.entityDistanceScaling")
        .setBinding((options, value) -> options.getEntityDistanceScaling().setValue(value),
            options -> options.getEntityDistanceScaling().getValue())
        .setRange(0.5, 5.0));
    put("guiScale", RangedIntOption.createRangedIntBuilder("guiScale", GameOptions.class)
        .setDisplayName("options.guiScale")
        .setBinding((options, value) -> {
              options.getGuiScale().setValue(value);
              MinecraftClient.getInstance().onResolutionChanged();
            },
            options -> options.getGuiScale().getValue())
        .setRange(() -> 0, () -> MinecraftClient.getInstance().getWindow().calculateScaleFactor(0,
            MinecraftClient.getInstance().forcesUnicodeFont())));
    put("particles", EnumOption.createEnumBuilder(ParticlesMode.class, GameOptions.class, "particles")
        .setDisplayName("options.particles")
        .setBinding((options, value) -> options.getParticles().setValue(value),
            options -> options.getParticles().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put(ParticlesMode.ALL.getTranslationKey(), ParticlesMode.ALL);
            put(ParticlesMode.DECREASED.getTranslationKey(), ParticlesMode.DECREASED);
            put(ParticlesMode.MINIMAL.getTranslationKey(), ParticlesMode.MINIMAL);
          }
        }));
    put("maxFps", RangedIntOption.createRangedIntBuilder("maxFps", GameOptions.class)
        .setDisplayName("options.framerateLimit")
        .setBinding((options, value) -> {
              options.getMaxFps().setValue(value);
              MinecraftClient.getInstance().getWindow().setFramerateLimit(value);
            },
            options -> options.getMaxFps().getValue()));
    // TODO: Set description with lambda
    put("graphicsMode", EnumOption.createEnumBuilder(GraphicsMode.class, GameOptions.class, "graphicsMode")
        .setDisplayName("options.graphics")
        .setBinding((options, value) -> options.getGraphicsMode().setValue(value),
            options -> options.getGraphicsMode().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put(GraphicsMode.FAST.getTranslationKey(), GraphicsMode.FAST);
            put(GraphicsMode.FANCY.getTranslationKey(), GraphicsMode.FANCY);
            put(GraphicsMode.FABULOUS.getTranslationKey(), GraphicsMode.FABULOUS);
          }
        })
        .setReloaders(RequiresRendererReload.createBuilder()));
    // TODO: Set description with lambda
    put("ao", BooleanOption.createBooleanBuilder("ao", GameOptions.class)
        .setDisplayName("options.ao")
        .setBinding((options, value) -> options.getAo().setValue(value),
            options -> options.getAo().getValue())
        .setReloaders(RequiresRendererReload.createBuilder()));
    // TODO: Set description with lambda
    put("prioritizeChunkUpdates", EnumOption.createEnumBuilder(ChunkBuilderMode.class, GameOptions.class,
        "prioritizeChunkUpdates")
        .setDisplayName("options.prioritizeChunkUpdates")
        .setBinding((options, value) -> options.getChunkBuilderMode().setValue(value),
            options -> options.getChunkBuilderMode().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put(ChunkBuilderMode.NONE.getTranslationKey(), ChunkBuilderMode.NONE);
            put(ChunkBuilderMode.PLAYER_AFFECTED.getTranslationKey(), ChunkBuilderMode.PLAYER_AFFECTED);
            put(ChunkBuilderMode.NEARBY.getTranslationKey(), ChunkBuilderMode.NEARBY);
          }
        })
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("biomeBlendRadius", RangedIntOption.createRangedIntBuilder("biomeBlendRadius", GameOptions.class)
        .setDisplayName("options.biomeBlendRadius")
        .setBinding((options, value) -> options.getBiomeBlendRadius().setValue(value),
            options -> options.getBiomeBlendRadius().getValue())
        .setRange(0, 7)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("renderClouds", EnumOption.createEnumBuilder(CloudRenderMode.class, GameOptions.class, "renderClouds")
        .setDisplayName("options.renderClouds")
        .setBinding((options, value) -> options.getCloudRenderMode().setValue(value),
            options -> options.getCloudRenderMode().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put(CloudRenderMode.OFF.getTranslationKey(), CloudRenderMode.OFF);
            put(CloudRenderMode.FAST.getTranslationKey(), CloudRenderMode.FAST);
            put(CloudRenderMode.FANCY.getTranslationKey(), CloudRenderMode.FANCY);
          }
        })
        .setReloaders(RequiresRendererReload.createBuilder()));
    // TODO Reload language properly
    put("lang", OptionImpl.createBuilder(String.class, GameOptions.class, "lang")
        .setDisplayName("options.language")
        .setBinding((options, value) -> options.language = value,
            options -> options.language));
    // TODO: Need to add a way to give lambda as AssociationMap key
//    List<String> soundDeviceList = Stream.concat(Stream.of(""), MinecraftClient.getInstance().getSoundManager().getSoundDevices().stream()).toList();
//    put("soundDevice", EnumOption.createEnumBuilder(String.class, GameOptions.class, "soundDevice")
//        .setDisplayName("options.audioDevice")
//        .setBinding((options, value) -> options.getSoundDevice().setValue(value),
//            options -> options.getSoundDevice().getValue())
//        .addTranslatedAssociationMap(new LinkedHashMap<>() {
//          {
//            for (String soundDevice : soundDeviceList) {
//              put(soundDevice, soundDevice);
//            }
//          }
//        }));
    put("chatVisibility", EnumOption.createEnumBuilder(ChatVisibility.class, GameOptions.class, "chatVisibility")
        .setDisplayName("options.chat.visibility")
        .setBinding((options, value) -> options.getChatVisibility().setValue(value),
            options -> options.getChatVisibility().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put(ChatVisibility.FULL.getTranslationKey(), ChatVisibility.FULL);
            put(ChatVisibility.SYSTEM.getTranslationKey(), ChatVisibility.SYSTEM);
            put(ChatVisibility.HIDDEN.getTranslationKey(), ChatVisibility.HIDDEN);
          }
        }));
    put("chatOpacity", RangedDoubleOption.createRangedDoubleBuilder("chatOpacity", GameOptions.class)
        .setDisplayName("options.chat.opacity")
        .setBinding((options, value) -> options.getChatOpacity().setValue(value),
            options -> options.getChatOpacity().getValue())
        .setRange(0.0, 1.0));
    put("chatLineSpacing", RangedDoubleOption.createRangedDoubleBuilder("chatLineSpacing", GameOptions.class)
        .setDisplayName("options.chat.line_spacing")
        .setBinding((options, value) -> options.getChatLineSpacing().setValue(value),
            options -> options.getChatLineSpacing().getValue())
        .setRange(0.0, 1.0));
    put("textBackgroundOpacity", RangedDoubleOption.createRangedDoubleBuilder("textBackgroundOpacity", GameOptions.class)
        .setDisplayName("options.accessibility.text_background_opacity")
        .setBinding((options, value) -> options.getTextBackgroundOpacity().setValue(value),
            options -> options.getTextBackgroundOpacity().getValue())
        .setRange(0.0, 1.0));
    put("backgroundForChatOnly", BooleanOption.createBooleanBuilder("backgroundForChatOnly", GameOptions.class)
        .setDisplayName("options.accessibility.text_background")
        .setBinding((options, value) -> options.getBackgroundForChatOnly().setValue(value),
            options -> options.getBackgroundForChatOnly().getValue()));
    put("advancedItemTooltips", BooleanOption.createBooleanBuilder("advancedItemTooltips", GameOptions.class)
        .setDisplayName("access_options.minecraft.options.advanced_tooltips")
        .setDescription("access_options.minecraft.options.advanced_tooltips.description")
        .setBinding((options, value) -> options.advancedItemTooltips = value,
            options -> options.advancedItemTooltips));
    put("pauseOnLostFocus", BooleanOption.createBooleanBuilder("pauseOnLostFocus", GameOptions.class)
        .setDisplayName("access_options.minecraft.options.pause_on_lost_focus")
        .setDescription("access_options.minecraft.options.pause_on_lost_focus.description")
        .setBinding((options, value) -> options.pauseOnLostFocus = value,
            options -> options.pauseOnLostFocus));
    put("chatHeightFocused", RangedDoubleOption.createRangedDoubleBuilder("chatHeightFocused", GameOptions.class)
        .setDisplayName("options.chat.height.focused")
        .setBinding((options, value) -> options.getChatHeightFocused().setValue(value),
            options -> options.getChatHeightFocused().getValue())
        .setRange(0.0, 1.0));
    put("chatDelay", RangedDoubleOption.createRangedDoubleBuilder("chatDelay", GameOptions.class)
        .setDisplayName("options.chat.delay_instant")
        .setBinding((options, value) -> options.getChatDelay().setValue(value),
            options -> options.getChatDelay().getValue())
        .setRange(0.0, 6.0));
    put("chatHeightUnfocused", RangedDoubleOption.createRangedDoubleBuilder("chatHeightUnfocused", GameOptions.class)
        .setDisplayName("options.chat.height.unfocused")
        .setBinding((options, value) -> options.getChatHeightUnfocused().setValue(value),
            options -> options.getChatHeightUnfocused().getValue())
        .setRange(0.0, 1.0));
    put("chatScale", RangedDoubleOption.createRangedDoubleBuilder("chatScale", GameOptions.class)
        .setDisplayName("options.chat.scale")
        .setBinding((options, value) -> options.getChatScale().setValue(value),
            options -> options.getChatScale().getValue())
        .setRange(0.0, 1.0));
    put("chatWidth", RangedDoubleOption.createRangedDoubleBuilder("chatWidth", GameOptions.class)
        .setDisplayName("options.chat.width")
        .setBinding((options, value) -> options.getChatWidth().setValue(value),
            options -> options.getChatWidth().getValue())
        .setRange(0.0, 1.0));
    put("notificationDisplayTime", RangedDoubleOption.createRangedDoubleBuilder("notificationDisplayTime", GameOptions.class)
        .setDisplayName("options.notifications.display_time")
        .setBinding((options, value) -> options.getNotificationDisplayTime().setValue(value),
            options -> options.getNotificationDisplayTime().getValue())
        .setRange(0.5, 10.0));
    put("mipmapLevels", RangedIntOption.createRangedIntBuilder("mipmapLevels", GameOptions.class)
        .setDisplayName("options.mipmapLevels")
        .setBinding((options, value) -> options.getMipmapLevels().setValue(value),
            options -> options.getMipmapLevels().getValue())
        .setRange(0, 4)
        .setReloaders(RequiresRendererReload.createBuilder()));
    put("mainHand", EnumOption.createEnumBuilder(Arm.class, GameOptions.class, "mainHand")
        .setDisplayName("options.mainHand")
        .setBinding((options, value) -> options.getMainArm().setValue(value),
            options -> options.getMainArm().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put(Arm.LEFT.getTranslationKey(), Arm.LEFT);
            put(Arm.RIGHT.getTranslationKey(), Arm.RIGHT);
          }
        }));
    put("attackIndicator", EnumOption.createEnumBuilder(AttackIndicator.class, GameOptions.class, "attackIndicator")
        .setDisplayName("options.attackIndicator")
        .setBinding((options, value) -> options.getAttackIndicator().setValue(value),
            options -> options.getAttackIndicator().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put(AttackIndicator.OFF.getTranslationKey(), AttackIndicator.OFF);
            put(AttackIndicator.CROSSHAIR.getTranslationKey(), AttackIndicator.CROSSHAIR);
            put(AttackIndicator.HOTBAR.getTranslationKey(), AttackIndicator.HOTBAR);
          }
        }));
    put("narrator", EnumOption.createEnumBuilder(NarratorMode.class, GameOptions.class, "narrator")
        .setDisplayName("options.narrator")
        .setBinding((options, value) -> options.getNarrator().setValue(value),
            options -> options.getNarrator().getValue())
        .addTranslatedAssociationMap(new LinkedHashMap<>() {
          {
            put("options.narrator.off", NarratorMode.OFF);
            put("options.narrator.all", NarratorMode.ALL);
            put("options.narrator.chat", NarratorMode.CHAT);
            put("options.narrator.system", NarratorMode.SYSTEM);
          }
        }));
    put("tutorialStep", EnumOption.createEnumBuilder(TutorialStep.class, GameOptions.class, "tutorialStep")
        .setDisplayName("access_options.minecraft.options.tutorial_step")
        .setBinding((options, value) -> options.tutorialStep = value,
            options -> options.tutorialStep)
        .addAssociationMap(new LinkedHashMap<>() {
          {
            put("movement", TutorialStep.MOVEMENT);
            put("find_tree", TutorialStep.FIND_TREE);
            put("punch_tree", TutorialStep.PUNCH_TREE);
            put("open_inventory", TutorialStep.OPEN_INVENTORY);
            put("craft_planks", TutorialStep.CRAFT_PLANKS);
            put("none", TutorialStep.NONE);
          }
        }));
    put("mouseWheelSensitivity", RangedDoubleOption.createRangedDoubleBuilder("mouseWheelSensitivity", GameOptions.class)
        .setDisplayName("options.mouseWheelSensitivity")
        .setBinding((options, value) -> options.getMouseWheelSensitivity().setValue(value),
            options -> options.getMouseWheelSensitivity().getValue())
        .setRange(1.0, 10.0));
    put("rawMouseInput", BooleanOption.createBooleanBuilder("rawMouseInput", GameOptions.class)
        .setDisplayName("options.rawMouseInput")
        .setBinding((options, value) -> options.getRawMouseInput().setValue(value),
            options -> options.getRawMouseInput().getValue()));
    put("skipMultiplayerWarning", BooleanOption.createBooleanBuilder("skipMultiplayerWarning", GameOptions.class)
        .setDisplayName("access_options.minecraft.options.skip_multiplayer_warning")
        .setBinding((options, value) -> options.skipMultiplayerWarning = value,
            options -> options.skipMultiplayerWarning));
    put("skipRealms32bitWarning", BooleanOption.createBooleanBuilder("skipRealms32bitWarning", GameOptions.class)
        .setDisplayName("access_options.minecraft.options.skip_realms_32_bit_warning")
        .setBinding((options, value) -> options.skipRealms32BitWarning = value,
            options -> options.skipRealms32BitWarning));
    put("hideMatchedNames", BooleanOption.createBooleanBuilder("hideMatchedNames", GameOptions.class)
        .setDisplayName("options.hideMatchedNames")
        .setDescription("options.hideMatchedNames.tooltip")
        .setBinding((options, value) -> options.getHideMatchedNames().setValue(value),
            options -> options.getHideMatchedNames().getValue()));
    put("hideBundleTutorial", BooleanOption.createBooleanBuilder("hideBundleTutorial", GameOptions.class)
        .setDisplayName("access_options.minecraft.options.hide_bundle_tutorial")
        .setBinding((options, value) -> options.hideBundleTutorial = value,
            options -> options.hideBundleTutorial));
    put("showAutosaveIndicator", BooleanOption.createBooleanBuilder("showAutosaveIndicator", GameOptions.class)
        .setDisplayName("access_options.minecraft.options.show_auto_save_indicator")
        .setBinding((options, value) -> options.getShowAutosaveIndicator().setValue(value),
            options -> options.getShowAutosaveIndicator().getValue()));
  }};
  public MinecraftStorageSupplier() {
    super(
        optionsAccessHandler -> new OptionsStorageImpl<>("minecraft", () -> MinecraftClient.getInstance().options, () -> MinecraftClient.getInstance().options.write(), new HashMap<>(), optionsAccessHandler),
        "minecraft");
    this.storageSupplier = optionsAccessHandler -> new OptionsStorageImpl<>("minecraft", () -> MinecraftClient.getInstance().options, () -> MinecraftClient.getInstance().options.write(), optionsBuilders, optionsAccessHandler);
  }
}

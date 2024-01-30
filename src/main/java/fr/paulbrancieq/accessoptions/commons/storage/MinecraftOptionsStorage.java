package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

public class MinecraftOptionsStorage extends OptionsStorageImpl<GameOptions> {
  private final MinecraftClient client;

  public MinecraftOptionsStorage() {
    super("minecraft");
    this.client = MinecraftClient.getInstance();
    this.registerOption("autoJump", OptionImpl.createBuilder(Boolean.class, this)
        .setName(Text.of("Auto Jump"))
        .setTooltip(Text.of("Automatically jump when running into a block"))
        .setBinding((options, value) -> options.getAutoJump().setValue(value), options -> options.getAutoJump().getValue())
        .setValueFromString(Boolean::parseBoolean)
        .setEnabled(true)
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

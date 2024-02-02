package fr.paulbrancieq.accessoptions.commons.storage;

import fr.paulbrancieq.accessoptions.commons.options.OptionImpl;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import net.minecraft.text.Text;

public class SodiumOptionsStorage extends OptionsStorageImpl<SodiumGameOptions> {
    public SodiumOptionsStorage() {
      super("sodium");
      this.registerOption("weather", OptionImpl.createBuilder(SodiumGameOptions.GraphicsQuality.class, this, "weather")
          .setName(Text.translatable("soundCategory.weather"))
          .setTooltip(Text.translatable("sodium.options.weather_quality.tooltip"))
          .setBinding((options, value) -> options.quality.weatherQuality = value, options -> options.quality.weatherQuality)
          .setValueFromString(SodiumGameOptions.GraphicsQuality::valueOf)
          .setEnabled(true)
          .build());
    }

    @Override
    public SodiumGameOptions getData() {
        return SodiumClientMod.options();
    }

    @Override
    public void save() {
      // TODO
    }
}

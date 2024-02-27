package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.commons.options.Option;
import fr.paulbrancieq.accessoptions.commons.reloader.AskConfirmation;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.text.Text;

public class ForceRequiresGameRestart extends GenericReloader implements AskConfirmation {
  protected ForceRequiresGameRestart(Builder builder) {
    super(builder);
  }

  @Override
  public BooleanConsumer getPromptAnswerConsumer(Option<?, ?> option) {
    return (prompt) -> {
      if (!prompt) {
        this.handler.resetOption(option);
        AccessOptions.getLogger().warn("User refused to restart the game, changes to " + option.getDisplayName() +
            " from " + option.getStorage().getStorageId() + " storage will not be applied.");
      }
    };
  }

  @Override
  public Text getName() {
    return Text.of("Requires immediate game restart");
  }

  @Override
  public Text getConfirmationText() {
    return Text.of("The changes you made require an immediate game restart to be applied. Do you want to restart " +
        "the game now? If you refuse, the changes will be lost.");
  }

  public static class Builder extends GenericReloader.Builder<Builder> {
    public Builder() {
      super();
      setRunnable((handler) -> handler.setRestartNeeded(true));
    }

    @Override
    public ForceRequiresGameRestart build() {
      return new ForceRequiresGameRestart(this);
    }
  }
}

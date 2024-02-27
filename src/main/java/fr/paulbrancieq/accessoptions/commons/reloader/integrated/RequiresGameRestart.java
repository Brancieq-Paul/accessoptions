package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.AccessOptions;

import fr.paulbrancieq.accessoptions.commons.options.Option;
import fr.paulbrancieq.accessoptions.commons.reloader.AskConfirmation;
import fr.paulbrancieq.accessoptions.commons.reloader.GenericReloader;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.text.Text;

import java.util.List;

public class RequiresGameRestart extends GenericReloader implements AskConfirmation {
  protected RequiresGameRestart(Builder builder) {
    super(builder);
  }

  @Override
  public BooleanConsumer getPromptAnswerConsumer(Option<?, ?> option) {
    return (prompt) -> {
      if (!prompt) {
        AccessOptions.getLogger().warn("User refused to restart the game, changes to " + option.getDisplayName() +
            " from " + option.getStorage().getStorageId() + " storage will not be effective until the game is restarted.");
      }
    };
  }

  @Override
  public Text getName() {
    return Text.of("Requires game restart");
  }

  @Override
  public Text getConfirmationText() {
    return Text.of("The changes you made require game restart to be applied. Do you want to restart " +
        "the game now? If you refuse, the changes will not be effective until the game is restarted.");
  }

  @Override
  public List<Class<? extends Reloader>> getDirectParents() {
    return List.of(ForceRequiresGameRestart.class);
  }

  @SuppressWarnings("unused")
  public static Builder createBuilder() {
    return new Builder();
  }

  public static class Builder extends GenericReloader.Builder<Builder> {
    public Builder() {
      super();
      setRunnable((handler) -> handler.setRestartNeeded(true));
    }

    @Override
    public RequiresGameRestart build() {
      return new RequiresGameRestart(this);
    }
  }
}

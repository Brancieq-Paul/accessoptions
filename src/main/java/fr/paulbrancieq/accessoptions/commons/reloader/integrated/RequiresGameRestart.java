package fr.paulbrancieq.accessoptions.commons.reloader.integrated;

import fr.paulbrancieq.accessoptions.AccessOptions;
import fr.paulbrancieq.accessoptions.OptionsAccessHandler;

import fr.paulbrancieq.accessoptions.commons.options.Option;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class RequiresGameRestart extends ForceRequiresGameRestart {

  public RequiresGameRestart(OptionsAccessHandler handler) {
    super(handler);
  }

  @Override
  public BooleanConsumer getPromptAnswerConsumer(Option<?> option) {
    return (prompt) -> {
      if (!prompt) {
        AccessOptions.getLogger().warn("User refused to restart the game, changes to " + option.getName() +
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
    return Text.of("The changes you made require game restart to be applied. Do you want to restart" +
        "the game now? If you refuse, the changes will not be effective until the game is restarted.");
  }
}

package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.commons.options.Option;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public interface AskConfirmation {
  BooleanConsumer getPromptAnswerConsumer(Option<?> option);
  @NotNull Text getName();
  @NotNull Text getConfirmationText();
}

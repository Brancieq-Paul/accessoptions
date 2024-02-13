package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.commons.options.Option;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.text.Text;

public interface AskConfirmation {
  BooleanConsumer getPromptAnswerConsumer(Option<?, ?> option);
  Text getName();
  Text getConfirmationText();
}

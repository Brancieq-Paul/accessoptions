package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.exeptions.ValueModificationException;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.text.Text;

public interface Option<T> {
  Text getName();

  Text getTooltip();

  T getValue();

  void setValue(Object value) throws ValueModificationException.OptionTypeMismatch;

  void reset();

  OptionsStorage<?> getStorage();

  boolean isAvailable();

  boolean hasChanged();

  void applyChanges() throws ValueModificationException.OptionNotModified;

  // TODO
  // Collection<OptionFlag> getFlags();
}

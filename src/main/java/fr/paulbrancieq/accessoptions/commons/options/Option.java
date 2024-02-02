package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.text.Text;

import java.util.Collection;

public interface Option<T> {
  Text getName();

  Text getTooltip();

  T getValue();

  void setValue(Object value) throws AccessOptionsException.OptionTypeMismatch;

  void reset();

  OptionsStorage<?> getStorage();

  boolean isAvailable();

  boolean hasChanged();

  void applyChanges() throws AccessOptionsException.OptionNotModified;

  Collection<Reloader> getReloaders();
}

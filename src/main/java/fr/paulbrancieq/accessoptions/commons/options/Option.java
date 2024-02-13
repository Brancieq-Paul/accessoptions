package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;
import net.minecraft.text.Text;

import java.util.Collection;

public interface Option<S, T> {
  Text getName();

  @SuppressWarnings("unused")
  T getValue();

  void setValue(Object value) throws AccessOptionsException.OptionTypeMismatch, ValueVerificationException, AccessOptionsException.OptionNotModified;

  void reset();

  OptionsStorage<S> getStorage();

  @SuppressWarnings("unused")
  boolean isAvailable();

  boolean hasChanged();

  void applyChanges() throws AccessOptionsException.OptionNotModified;

  Collection<Reloader> getReloaders();
}

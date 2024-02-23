package fr.paulbrancieq.accessoptions.commons.options;

import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.exeptions.ValueVerificationException;
import fr.paulbrancieq.accessoptions.commons.reloader.Reloader;
import fr.paulbrancieq.accessoptions.commons.storage.OptionsStorage;

import java.util.Collection;

public interface Option<S, T> {
  String getDisplayName();
  @SuppressWarnings("unused")
  String getId();
  @SuppressWarnings("unused")
  String getDescription();
  @SuppressWarnings("unused")
  T getPendingValue();
  @SuppressWarnings("unused")
  T getOriginalValue();
  void modifyPendingValue(Object value) throws AccessOptionsException.OptionTypeMismatch, ValueVerificationException, AccessOptionsException.PendingOptionNotDifferent;
  void reset();
  OptionsStorage<S> getStorage();
  @SuppressWarnings("unused")
  boolean isAvailable();
  boolean hasChanged();
  void applyPendingValue() throws AccessOptionsException.PendingOptionNotDifferent;
  Collection<Reloader> getReloaders();
}

package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.commons.options.Option;

import java.util.Collection;
import java.util.List;

public interface Reloader {
  void run();
  Boolean isChildOf(Reloader reloader);
  Boolean isSameAs(Reloader reloader);
  Collection<Option<?, ?>> getAssociatedModifiedOptions();
  void addAssociatedModifiedOption(Option<?, ?> option);
  List<Class<? extends Reloader>> getParents();
  List<Class<? extends Reloader>> getParents(List<Class<? extends Reloader>> passedReloaderClasses);
  List<Class<? extends Reloader>> getDirectParents();
}

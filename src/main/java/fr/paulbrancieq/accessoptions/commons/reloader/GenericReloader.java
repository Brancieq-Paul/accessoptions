package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.options.Option;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericReloader implements Reloader {
  private Runnable runnable;
  private final Collection<Option<?, ?>> associatedModifiedOptions = new ArrayList<>();
  protected final OptionsAccessHandler handler;

  /**
   * Constructor for the GenericReloader class. Create an empty instance. Used for internal purposes.
   * All reloaders should at least have this empty constructor.
   */
  public GenericReloader() {
    handler = null;
  }
  public GenericReloader(Runnable runnable, OptionsAccessHandler handler) {
    setRunnable(runnable);
    this.handler = handler;
  }
  @Override
  public void run() {
    this.runnable.run();
  }
  @Override
  public Boolean isChildOf(Reloader otherReloader) {
    try {
      return getParents().stream().noneMatch(parent -> parent == otherReloader.getClass());
    } catch (AccessOptionsException.ReloaderParentingLoop e) {
      throw new RuntimeException(e);
    }
  }
  @Override
  public Boolean isSameAs(Reloader otherReloader) {
    return otherReloader.getClass() == this.getClass();
  }
  @Override
  public List<Class<? extends Reloader>> getDirectParents() {
    return new ArrayList<>();
  }
  @Override
  public List<Class<? extends Reloader>> getParents() throws AccessOptionsException.ReloaderParentingLoop {
    List<Class<? extends Reloader>> passedReloadersClass = new ArrayList<>();
    return getParents(passedReloadersClass);
  }
  @Override
  public List<Class<? extends Reloader>> getParents(List<Class<? extends Reloader>> passedReloaderClasses)
      throws AccessOptionsException.ReloaderParentingLoop {
    if (passedReloaderClasses.contains(this.getClass())) {
      throw new AccessOptionsException.ReloaderParentingLoop(this.getClass().getSimpleName());
    }
    passedReloaderClasses.add(this.getClass());
    List<Class<? extends Reloader>> parents = new ArrayList<>(getDirectParents());
    for (Class<? extends Reloader> parent : parents) {
      try {
        parents.addAll(parent.getDeclaredConstructor().newInstance().getParents(passedReloaderClasses));
      } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        throw new RuntimeException("Error while getting parents of reloader " + this.getClass().getName(), e);
      } catch (AccessOptionsException.ReloaderParentingLoop e) {
        throw new AccessOptionsException.ReloaderParentingLoop(this.getClass().getSimpleName(), e);
      }
    }
    return parents;
  }

  private void setRunnable(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  public Collection<Option<?, ?>> getAssociatedModifiedOptions() {
    return associatedModifiedOptions;
  }

  @Override
  public void addAssociatedModifiedOption(Option<?, ?> option) {
    associatedModifiedOptions.add(option);
  }
}

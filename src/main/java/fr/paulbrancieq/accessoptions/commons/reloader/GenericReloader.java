package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.exeptions.AccessOptionsException;
import fr.paulbrancieq.accessoptions.commons.options.Option;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericReloader implements Reloader {
  private ReloadRunner reloadRunner;
  private final Collection<Option<?, ?>> associatedModifiedOptions = new ArrayList<>();
  protected final OptionsAccessHandler handler;

  protected GenericReloader(Builder<?> builder) {
    setReloadRunner(builder.runnable);
    Validate.notNull(builder.handler, "OptionsAccessHandler must be specified");
    this.handler = builder.handler;
  }

  @Override
  public void run() {
    this.reloadRunner.run(handler);
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

  private void setReloadRunner(ReloadRunner reloadRunner) {
    this.reloadRunner = reloadRunner;
  }

  @Override
  public Collection<Option<?, ?>> getAssociatedModifiedOptions() {
    return associatedModifiedOptions;
  }

  @Override
  public void addAssociatedModifiedOption(Option<?, ?> option) {
    associatedModifiedOptions.add(option);
  }

  @SuppressWarnings("unused")
  public static Builder<?> createBuilder() {
    return new Builder<>();
  }

  public static class Builder<U extends Builder<?>> {
    protected OptionsAccessHandler handler;
    protected ReloadRunner runnable;

    protected Builder() {
    }

    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    public U setOptionsAccessHandler(OptionsAccessHandler handler) {
      this.handler = handler;
      return (U) this;
    }

    @SuppressWarnings({"unchecked","UnusedReturnValue"})
    protected U setRunnable(ReloadRunner runnable) {
      this.runnable = runnable;
      return (U) this;
    }

    public GenericReloader build() {
      throw new UnsupportedOperationException("This method should be overridden by the subclass");
    }
  }
}

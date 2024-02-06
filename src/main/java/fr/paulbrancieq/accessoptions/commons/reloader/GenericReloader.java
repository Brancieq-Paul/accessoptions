package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GenericReloader implements Reloader {
  private Runnable runnable;
  private final Collection<Option<?>> associatedModifiedOptions = new ArrayList<>();
  protected final OptionsAccessHandler handler;
  private final List<Class<? extends Reloader>> parents = new ArrayList<>();

  @SafeVarargs
  public GenericReloader(Runnable runnable, OptionsAccessHandler handler, Class<? extends Reloader>... parents) {
    setRunnable(runnable);
    this.handler = handler;
    Arrays.stream(parents).forEach((parent) -> {
      if (parent != null) {
        this.parents.add(parent);
      }
    });
  }

  @Override
  public void run() {
    this.runnable.run();
  }

  @Override
  public Boolean isChildOf(Reloader otherReloader) {
    // Verify if otherReloader is the parent class of this
    return parents.contains(otherReloader.getClass());
  }

  @Override
  public Boolean isSameAs(Reloader otherReloader) {
    // Verify if otherReloader is the same class as this
    return otherReloader.getClass() == this.getClass();
  }

  private void setRunnable(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  public Collection<Option<?>> getAssociatedModifiedOptions() {
    return associatedModifiedOptions;
  }

  @Override
  public void addAssociatedModifiedOption(Option<?> option) {
    associatedModifiedOptions.add(option);
  }
}

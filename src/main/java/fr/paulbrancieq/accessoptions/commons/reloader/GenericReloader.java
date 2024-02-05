package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;
import fr.paulbrancieq.accessoptions.commons.options.Option;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collection;

public class GenericReloader implements Reloader {
  private Runnable runnable;
  private final Collection<Option<?>> associatedModifiedOptions = new ArrayList<>();
  protected final OptionsAccessHandler handler;

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
    // Verify if otherReloader is the parent class of this
    if (otherReloader.getClass() != this.getClass() && otherReloader.getClass().isAssignableFrom(this.getClass())) {
      return this.runnable.equals(((GenericReloader) otherReloader).runnable);
    }
    return false;
  }

  @Override
  public Boolean isSameAs(Reloader otherReloader) {
    // Verify if otherReloader is the same class as this
    return otherReloader.getClass() == this.getClass();
  }

  protected void setRunnable(Runnable runnable) {
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

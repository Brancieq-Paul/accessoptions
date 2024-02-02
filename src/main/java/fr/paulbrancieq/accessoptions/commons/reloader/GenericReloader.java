package fr.paulbrancieq.accessoptions.commons.reloader;

public class GenericReloader implements Reloader {
  private final Runnable runnable;

  public GenericReloader(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  public void run() {
    this.runnable.run();
  }

  @Override
  public Boolean isChildOf(Reloader otherReloader) {
    // Verify if otherReloader is the same class or parent class of this
    if (otherReloader.getClass().isAssignableFrom(this.getClass())) {
      return this.runnable.equals(((GenericReloader) otherReloader).runnable);
    }
    return false;
  }
}

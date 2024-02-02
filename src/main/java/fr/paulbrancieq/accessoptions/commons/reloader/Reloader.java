package fr.paulbrancieq.accessoptions.commons.reloader;

public interface Reloader {
  void run();
  Boolean isChildOf(Reloader reloader);
}

package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;

@FunctionalInterface
public interface ReloadRunner {
  void run(OptionsAccessHandler handler);
}

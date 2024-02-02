package fr.paulbrancieq.accessoptions.commons.reloader;

import fr.paulbrancieq.accessoptions.OptionsAccessHandler;

public class NeedGameRestart extends GenericReloader {
    public NeedGameRestart() {
        super(() -> {
          OptionsAccessHandler.setRestartNeeded(true);
        });
    }
}

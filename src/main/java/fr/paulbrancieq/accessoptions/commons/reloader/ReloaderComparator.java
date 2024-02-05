package fr.paulbrancieq.accessoptions.commons.reloader;

import java.util.Comparator;

public class ReloaderComparator implements Comparator<Reloader> {
  public int compare(Reloader o1, Reloader o2) {
    return o1.isChildOf(o2) ? -1 : (o2.isChildOf(o1) ? 1 : 0);
  }
}

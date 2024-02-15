package fr.paulbrancieq.accessoptions.commons.exeptions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AccessOptionsException extends Exception {

  public AccessOptionsException(String message) {
    super(message);
  }

  public static class OptionTypeMismatch extends AccessOptionsException {
    public OptionTypeMismatch(String storageId, String optionId, String expectedType, String actualType) {
      super("Option type mismatch for storage \"" + storageId + "\" and option id \"" + optionId + "\". Expected type: " +
          expectedType + " Actual type: " + actualType);
    }
  }

  public static class PendingOptionNotDifferent extends AccessOptionsException {
    public PendingOptionNotDifferent(String storageId, String optionId) {
      super("Option is already set to the applied value for storage \"" + storageId + "\" and option id \"" +
          optionId + "\".");
    }
  }

  public static class OptionNotFound extends AccessOptionsException {
    public OptionNotFound(String storageId, String optionId) {
      super("Option not found for storage \"" + storageId + "\" and option id \"" + optionId + "\".");
    }
  }

  public static class OptionStorageNotFound extends AccessOptionsException {
    public OptionStorageNotFound(String storageId) {
      super("Option storage not found for storage \"" + storageId + "\".");
    }
  }

  public static class ReloaderParentingLoop extends AccessOptionsException {
    List<String> parentOfNext;
    static Function<List<String>, String> parentOfNextString = parentOfNext -> {
      StringBuilder parentOfNextString = new StringBuilder();
      for (String parent : parentOfNext) {
        parentOfNextString.append(parent).append(" parent of ");
      }
      return parentOfNextString.toString();
    };
    public ReloaderParentingLoop(String reloaderName) {
      super("Reloader parenting loop detected: " + reloaderName + ".");
      this.parentOfNext = new ArrayList<>() {{
        add(reloaderName);
      }};
    }
    public ReloaderParentingLoop(String reloaderName, ReloaderParentingLoop parentOfNext) {
      super("Reloader parenting loop detected: " + parentOfNextString.apply(parentOfNext.parentOfNext) + reloaderName + ".");
      this.parentOfNext = new ArrayList<>(parentOfNext.parentOfNext) {{
        add(reloaderName);
      }};
    }
  }
}

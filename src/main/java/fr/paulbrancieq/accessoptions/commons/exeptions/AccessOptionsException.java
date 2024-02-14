package fr.paulbrancieq.accessoptions.commons.exeptions;

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
}

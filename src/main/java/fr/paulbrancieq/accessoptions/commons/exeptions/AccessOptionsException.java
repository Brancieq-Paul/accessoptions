package fr.paulbrancieq.accessoptions.commons.exeptions;

public class AccessOptionsException extends Exception {

  public AccessOptionsException(String message) {
    super(message);
  }

  public static class OptionTypeMismatch extends AccessOptionsException {
    public OptionTypeMismatch(String modId, String optionId, String expectedType, String actualType) {
      super("Option type mismatch for mod id: " + modId + " and option id: " + optionId + " expected type: " + expectedType + " actual type: " + actualType);
    }
  }

  public static class OptionNotModified extends AccessOptionsException {
    public OptionNotModified(String modId, String optionId) {
      super("Option is already set to the applied value for mod id: " + modId + " and option id: " + optionId);
    }
  }

  public static class OptionNotFound extends AccessOptionsException {
    public OptionNotFound(String modId, String optionId) {
      super("Option not found for mod id: " + modId + " and option id: " + optionId);
    }
  }

  public static class OptionStorageNotFound extends AccessOptionsException {
    public OptionStorageNotFound(String modId) {
      super("Option storage not found for mod id: " + modId);
    }
  }
}

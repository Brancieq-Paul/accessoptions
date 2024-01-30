package fr.paulbrancieq.accessoptions.commons.exeptions;

public class ValueModificationException extends Exception {

  public ValueModificationException(String message) {
    super(message);
  }

  public static class OptionNotFound extends ValueModificationException {
    public OptionNotFound(String modId, String optionId) {
      super("Option not found for mod id: " + modId + " and option id: " + optionId);
    }
  }

  public static class OptionTypeMismatch extends ValueModificationException {
    public OptionTypeMismatch(String modId, String optionId, String expectedType, String actualType) {
      super("Option type mismatch for mod id: " + modId + " and option id: " + optionId + " expected type: " + expectedType + " actual type: " + actualType);
    }
  }

  public static class OptionNotModified extends ValueModificationException {
    public OptionNotModified(String modId, String optionId) {
      super("Applied value is the same as the actual value for mod id: " + modId + " and option id: " + optionId);
    }
  }
}

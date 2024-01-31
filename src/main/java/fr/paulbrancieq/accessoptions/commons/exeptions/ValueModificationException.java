package fr.paulbrancieq.accessoptions.commons.exeptions;

public class ValueModificationException extends Exception {

  public ValueModificationException(String message) {
    super(message);
  }

  public static class OptionTypeMismatch extends ValueModificationException {
    public OptionTypeMismatch(String modId, String optionId, String expectedType, String actualType) {
      super("Option type mismatch for mod id: " + modId + " and option id: " + optionId + " expected type: " + expectedType + " actual type: " + actualType);
    }
  }

  public static class OptionNotModified extends ValueModificationException {
    public OptionNotModified(String modId, String optionId) {
      super("Option is already set to the applied value for mod id: " + modId + " and option id: " + optionId);
    }
  }
}

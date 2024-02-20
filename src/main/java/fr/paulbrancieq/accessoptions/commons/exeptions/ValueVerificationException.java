package fr.paulbrancieq.accessoptions.commons.exeptions;

import java.util.Arrays;

public class ValueVerificationException extends AccessOptionsException {

  public ValueVerificationException(String message) {
    super(message);
  }

  public static class ValueNotInRange extends ValueVerificationException {
    public ValueNotInRange(String storageId, String optionId, Number value, Number min, Number max) {
      super("Value " + value + " for storage \"" + storageId + "\" and option id \"" + optionId + "\" is not in range. Min: " +
          min + " Max: " + max);
    }
  }

  public static class ValueNotInEnum extends ValueVerificationException {
    public ValueNotInEnum(String storageId, String optionId, Object value, Object[] enumValues) {
      super("Value " + value + " for storage \"" + storageId + "\" and option id \"" + optionId + "\" is not in enum." +
          "Enum values: " + Arrays.toString(enumValues));
    }
  }
}

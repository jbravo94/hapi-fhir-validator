package dev.heinzl;

public interface SimpleFhirValidationResult {
    boolean isValid();

    String getFormat();

    String getMessage();
}

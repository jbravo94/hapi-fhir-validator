package dev.heinzl;

public class SimpleFhirR4ValidationResult implements SimpleFhirValidationResult {
    private boolean isValid;
    private String format;
    private String message;

    public SimpleFhirR4ValidationResult(boolean isValid, String format, String message) {
        this.isValid = isValid;
        this.format = format;
        this.message = message;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}

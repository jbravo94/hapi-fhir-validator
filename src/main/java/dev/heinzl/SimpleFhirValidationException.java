package dev.heinzl;

import org.hl7.fhir.r4.model.OperationOutcome;

public abstract class SimpleFhirValidationException extends Exception {

    public SimpleFhirValidationException(String message) {
        super(message);
    }

    abstract OperationOutcome getOperationOutcome();
}

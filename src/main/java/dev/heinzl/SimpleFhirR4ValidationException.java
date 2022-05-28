package dev.heinzl;

import org.hl7.fhir.r4.model.OperationOutcome;

public class SimpleFhirR4ValidationException extends SimpleFhirValidationException {

    private OperationOutcome operationOutcome;

    public SimpleFhirR4ValidationException(OperationOutcome operationOutcome, String message) {
        super(message);
        this.operationOutcome = operationOutcome;
    }

    @Override
    public OperationOutcome getOperationOutcome() {
        return this.operationOutcome;
    }
}

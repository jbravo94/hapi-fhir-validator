package dev.heinzl;

import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.validation.ValidationResult;

public interface SimpleFhirValidator {
    SimpleFhirR4ValidationResult validateString(String input);

    ValidationResult validateBaseResource(IBaseResource baseResource);
}

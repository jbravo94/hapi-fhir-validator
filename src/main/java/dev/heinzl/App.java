package dev.heinzl;

import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.StringType;

import ca.uhn.fhir.validation.ValidationResult;

public class App {
    public static void main(String[] args) throws ClassNotFoundException {

        SimpleFhirValidator simpleFhirValidator = new SimpleFhirR4Validator();

        System.out.println("Validate HAPI FHIR Java Objects");

        Observation obs = new Observation();
        obs.getCode().addCoding().setSystem("http://loinc.org").setCode("12345-6");
        obs.setValue(new StringType("This is a value"));

        System.out.println("Input: " + obs.toString());

        ValidationResult validateBaseResource = simpleFhirValidator.validateBaseResource(obs);

        System.out.println("Is valid: " + validateBaseResource.isSuccessful());

        System.out.println("Validate JSON FHIR Objects");

        String input = "{" +
                "\"resourceType\" : \"Patient\"," +
                "  \"name\" : [{" +
                "    \"family\": \"Simpson\"" +
                "  }]" +
                "}";

        System.out.println("Input: " + input);

        SimpleFhirR4ValidationResult validationResult = simpleFhirValidator.validateString(input);

        System.out.println("Result: " + validationResult.getMessage());

        System.out.println("Validate XML FHIR Objects");

        input = "<Patient><active value=\"true\"/><active value=\"false\"/></Patient>";

        System.out.println("Input: " + input);

        validationResult = simpleFhirValidator.validateString(input);

        System.out.println("Result: " + validationResult.getMessage());

    }
}

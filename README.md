# Introduction

This project makes use of HAPI FHIR libraries and created a simple library to validate generic JSON, XML or HAPI FHIR Java objects via command line or directly in Java after adding in another project.

## This library implements several checks for plain JSON and XML files

- Simple identification if JSON, XML or other format is present
- Strict parsers enables error detection if wrong format is supplied e.g. corrupt JSON
- Validators enables error detection if schema is wrong e.g. mandatory fields are missing

## Supported functionalities

- Minimum Java 11
- Only R4 FHIR
- Only JSON and XML formats via command line
- Simple serializable POJO returned as response via main API
- FHIR Operationoutcome is returned as default response with possible issues or information

## Important classes

- Main API is implemented in class `src/main/java/dev/heinzl/SimpleFhirR4Validator.java`.
- The main class `src/main/java/dev/heinzl/HapiFhirValidator.java` facilitates the command line actions.
- Examples can be found in class `src/main/java/dev/heinzl/HapiFhirValidatorExamples.java`.

# Build

## Prerequisites

- Java 11
- Maven 3

## Compilation

Execute `mvn package`.

# Standalone usage via command line

## Examples

- `echo '{"resourceType" : "Patient", "name" : [{ "family": "Simpson" }]}' | java -jar hapifhirvalidator-1.0.0-shaded.jar`
- `echo '<Patient><active value="true"/></Patient>' | java -jar hapifhirvalidator-1.0.0-shaded.jar`

# Further Links

## Official Documentation

- https://hapifhir.io/hapi-fhir/docs/model/parsers.html
- https://hapifhir.io/hapi-fhir/docs/validation/instance_validator.html
- https://hapifhir.io/hapi-fhir/docs/validation/parser_error_handler.html

## Used jars

- https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-structures-r4/6.0.1/hapi-fhir-structures-r4-6.0.1.jar
- https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-base/6.0.1/hapi-fhir-base-6.0.1.jar
- https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-validation/6.0.1/hapi-fhir-validation-6.0.1.jar
- https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-validation-resources-r4/6.0.1/hapi-fhir-validation-resources-r4-6.0.1.jar

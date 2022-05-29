# Build

`mvn package`

# Usage

`echo '{"resourceType" : "Patient", "name" : [{ "family": "Simpson" }]}' | java -jar hapifhirvalidator-1.0.0-shaded.jar`

# Links

https://hapifhir.io/hapi-fhir/docs/model/parsers.html
https://hapifhir.io/hapi-fhir/docs/validation/instance_validator.html
https://hapifhir.io/hapi-fhir/docs/validation/parser_error_handler.html

# Needed jars

https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-structures-r4/6.0.1/hapi-fhir-structures-r4-6.0.1.jar
https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-base/6.0.1/hapi-fhir-base-6.0.1.jar
https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-validation/6.0.1/hapi-fhir-validation-6.0.1.jar
https://repo1.maven.org/maven2/ca/uhn/hapi/fhir/hapi-fhir-validation-resources-r4/6.0.1/hapi-fhir-validation-resources-r4-6.0.1.jar

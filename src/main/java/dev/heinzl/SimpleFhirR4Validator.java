package dev.heinzl;

import java.util.stream.Collectors;

import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.OperationOutcome.IssueSeverity;
import org.hl7.fhir.r4.model.OperationOutcome.IssueType;
import org.hl7.fhir.r4.model.OperationOutcome.OperationOutcomeIssueComponent;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.parser.JsonParser;
import ca.uhn.fhir.parser.StrictErrorHandler;
import ca.uhn.fhir.parser.XmlParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;

public class SimpleFhirR4Validator implements SimpleFhirValidator {

    private FhirContext ctx;
    private FhirValidator validator;
    private XmlParser xmlParser;
    private JsonParser jsonParser;

    public SimpleFhirR4Validator() {
        initContext();
        initValidator();
        initParsers();
    }

    private void initContext() {
        ctx = FhirContext.forR4();
    }

    private void initValidator() {
        ValidationSupportChain validationSupportChain = new ValidationSupportChain(
                new DefaultProfileValidationSupport(ctx),
                new InMemoryTerminologyServerValidationSupport(ctx),
                new CommonCodeSystemsTerminologyService(ctx));

        validator = ctx.newValidator();
        FhirInstanceValidator instanceValidator = new FhirInstanceValidator(validationSupportChain);
        instanceValidator.setAnyExtensionsAllowed(true);

        validator.registerValidatorModule(instanceValidator);
    }

    private void initParsers() {
        xmlParser = (XmlParser) ctx.newXmlParser();
        xmlParser.setParserErrorHandler(new StrictErrorHandler());

        jsonParser = (JsonParser) ctx.newJsonParser();
        jsonParser.setParserErrorHandler(new StrictErrorHandler());
    }

    private boolean isXml(String input) {
        return input != null && input.trim().startsWith("<");
    }

    private boolean isJson(String input) {
        return input != null && (input.trim().startsWith("{") || input.trim().startsWith("["));
    }

    @Override
    public SimpleFhirR4ValidationResult validateString(String input) {

        if (isXml(input)) {
            return handleXml(input);
        } else if (isJson(input)) {
            return handleJson(input);
        } else {
            return new SimpleFhirR4ValidationResult(false, "text/plain",
                    "Data format not supported - only valid XML and JSON!");
        }
    }

    @Override
    public ValidationResult validateBaseResource(IBaseResource baseResource) {
        return validator.validateWithResult(baseResource);
    }

    private SimpleFhirR4ValidationResult handleXml(String input) {
        return handleStructuredString(xmlParser, "application/xml", input);
    }

    private SimpleFhirR4ValidationResult handleJson(String input) {
        return handleStructuredString(jsonParser, "application/json", input);
    }

    private SimpleFhirR4ValidationResult handleStructuredString(IParser parser, String format, String input) {
        IBaseResource parsed = null;
        OperationOutcome oo = new OperationOutcome();
        boolean isValid = true;

        try {
            parsed = parser.parseResource(input);
        } catch (DataFormatException e) {
            isValid = false;
            oo = new OperationOutcome();
            OperationOutcomeIssueComponent issue = oo.addIssue();
            issue.setSeverity(IssueSeverity.FATAL);
            issue.setCode(IssueType.INVALID);
            issue.setDiagnostics(e.getMessage());
        }

        if (isValid) {
            try {
                oo = validateResource(parsed);
            } catch (SimpleFhirR4ValidationException e) {
                oo = e.getOperationOutcome();
                isValid = false;
            }
        }

        return new SimpleFhirR4ValidationResult(isValid, format, parser.encodeResourceToString(oo));
    }

    private OperationOutcome validateResource(IBaseResource baseResource) throws SimpleFhirR4ValidationException {
        ValidationResult result = validator.validateWithResult(baseResource);
        OperationOutcome operationOutcome = (OperationOutcome) result.toOperationOutcome();

        if (!result.isSuccessful()) {
            throw new SimpleFhirR4ValidationException(operationOutcome, getResultMessages(result));
        }

        return operationOutcome;
    }

    private String getResultMessages(ValidationResult result) {
        return result.getMessages().stream()
                .map(next -> ("Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - "
                        + next.getMessage()))
                .collect(Collectors.joining(", \n"));
    }
}

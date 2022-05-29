package dev.heinzl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class HapiFhirValidator {
    public static void main(String[] args) throws ClassNotFoundException {

        SimpleFhirValidator simpleFhirValidator = new SimpleFhirR4Validator();

        String input;

        try (InputStreamReader in = new InputStreamReader(System.in);
                BufferedReader buffer = new BufferedReader(in)) {
            input = buffer.lines().collect(Collectors.joining());

            System.out.println("Input:");
            System.out.println(input);

            SimpleFhirR4ValidationResult validationResult = simpleFhirValidator.validateString(input);

            System.out.println("Result:");
            System.out.println(validationResult.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

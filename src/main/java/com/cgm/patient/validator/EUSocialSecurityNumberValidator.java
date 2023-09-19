package com.cgm.patient.validator;

import com.cgm.patient.validator.annotation.EUSocialSecurityNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EUSocialSecurityNumberValidator implements ConstraintValidator<EUSocialSecurityNumber, String> {
    private static final Pattern EU_SOCIAL_SECURITY_PATTERN = Pattern.compile("^[A-Z0-9]+$");

    @Override
    public boolean isValid(String socialSecurityNumber, ConstraintValidatorContext context) {
        if (socialSecurityNumber == null) {
            return true;
        }
        return EU_SOCIAL_SECURITY_PATTERN.matcher(socialSecurityNumber).matches();
    }
}

package com.cgm.patient.validator;

import com.cgm.patient.validator.annotation.NoNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class NoNumbersValidator implements ConstraintValidator<NoNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !value.matches(".*\\d.*");
    }
}

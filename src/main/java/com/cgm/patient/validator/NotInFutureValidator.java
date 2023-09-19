package com.cgm.patient.validator;

import com.cgm.patient.validator.annotation.NotInFuture;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotInFutureValidator implements ConstraintValidator<NotInFuture, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime dateTime, ConstraintValidatorContext context) {
        if (dateTime == null) {
            return true;
        }
        return !dateTime.isAfter(LocalDateTime.now());
    }
}

package com.cgm.patient.validator.annotation;

import com.cgm.patient.validator.NoNumbersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoNumbersValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNumber {

    String message() default "No number is allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

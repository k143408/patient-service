package com.cgm.patient.validator.annotation;

import com.cgm.patient.validator.EUSocialSecurityNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = EUSocialSecurityNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EUSocialSecurityNumber {
    String message() default "Invalid EU social security number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

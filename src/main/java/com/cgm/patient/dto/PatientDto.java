package com.cgm.patient.dto;

import com.cgm.patient.validator.annotation.EUSocialSecurityNumber;
import com.cgm.patient.validator.annotation.NoNumber;
import com.cgm.patient.validator.annotation.NotInFuture;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PatientDto(
        @NotNull @NoNumber String firstName,
        @NotNull @NoNumber String lastName,
        @NotNull @NotInFuture LocalDateTime dateOfBirth,
        @NotNull @EUSocialSecurityNumber String socialSecurityNumber) {
}

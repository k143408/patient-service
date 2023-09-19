package com.cgm.patient.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static Stream<PatientDto> validPatientDtoProvider() {
        return Stream.of(
                new PatientDto("John", "Doe", LocalDateTime.now().minusDays(1), "1234567890"),
                new PatientDto("Alice", "Smith", LocalDateTime.now().minusDays(365), "9876543210")
        );
    }

    private static Stream<Arguments> invalidPatientDtoProvider() {
        return Stream.of(
                Arguments.of(new PatientDto(null, null, null, null), 4),
                Arguments.of(new PatientDto(null, "Doe", LocalDateTime.now(), "1234567890"), 1),
                Arguments.of(new PatientDto("John", null, LocalDateTime.now(), "1234567890"), 1),
                Arguments.of(new PatientDto("John", "Doe", null, "1234567890"), 1),
                Arguments.of(new PatientDto("John", "Doe", LocalDateTime.now(), null), 1),
                Arguments.of(new PatientDto("John123", "Doe", LocalDateTime.now(), "1234567890"), 1),
                Arguments.of(new PatientDto("John", "123Doe", LocalDateTime.now(), "1234567890"), 1),
                Arguments.of(new PatientDto("John", "Doe", LocalDateTime.now().plusDays(1), "1234567890"), 1),
                Arguments.of(new PatientDto("John", "Doe", LocalDateTime.now(), "invalidSSN"), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("validPatientDtoProvider")
    public void testValidPatientDto(PatientDto patientDto) {
        Set<ConstraintViolation<PatientDto>> violations = validator.validate(patientDto);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidPatientDtoProvider")
    public void testInvalidPatientDto(PatientDto patientDto , int expectedViolations) {
        Set<ConstraintViolation<PatientDto>> violations = validator.validate(patientDto);
        assertEquals(expectedViolations, violations.size());
    }
}
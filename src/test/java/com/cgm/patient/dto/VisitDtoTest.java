package com.cgm.patient.dto;

import com.cgm.patient.entity.helper.VisitReason;
import com.cgm.patient.entity.helper.VisitType;
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

class VisitDtoTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static Stream<VisitDto> validVisitDtoProvider() {
        return Stream.of(
                new VisitDto(1L, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, null),
                new VisitDto(2L, VisitType.DOCTOR_OFFICE, LocalDateTime.now(), VisitReason.URGENT, "No family history")
                // Add more valid test cases here
        );
    }

    private static Stream<Arguments> invalidVisitDtoProvider() {
        return Stream.of(
                Arguments.of(new VisitDto(null, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, null), 1), // Missing patientId
                Arguments.of(new VisitDto(-1L, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, null), 1), // Invalid patientId
                Arguments.of(new VisitDto(1L, null, LocalDateTime.now(), VisitReason.FIRST_VISIT, null), 1), // Missing visitType
                Arguments.of(new VisitDto(1L, VisitType.HOME, null, VisitReason.FIRST_VISIT, null), 1), // Missing dateTime
                Arguments.of(new VisitDto(1L, VisitType.HOME, LocalDateTime.now(), null, null), 1) // Missing reason
        );
    }

    @ParameterizedTest
    @MethodSource("validVisitDtoProvider")
    public void testValidVisitDto(VisitDto visitDto) {
        Set<ConstraintViolation<VisitDto>> violations = validator.validate(visitDto);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidVisitDtoProvider")
    public void testInvalidVisitDto(VisitDto visitDto, int expectedViolations) {
        Set<ConstraintViolation<VisitDto>> violations = validator.validate(visitDto);
        assertEquals(expectedViolations, violations.size());
    }
}
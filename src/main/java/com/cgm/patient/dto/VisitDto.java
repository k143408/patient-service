package com.cgm.patient.dto;

import com.cgm.patient.entity.helper.VisitReason;
import com.cgm.patient.entity.helper.VisitType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VisitDto(
        @NotNull
        @Min(0)
        Long patientId,
        @NotNull VisitType visitType,
        @NotNull LocalDateTime dateTime,
        @NotNull VisitReason reason,
        String familyHistory) {
}

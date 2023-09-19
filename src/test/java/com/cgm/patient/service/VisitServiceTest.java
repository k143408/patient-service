package com.cgm.patient.service;

import com.cgm.patient.dto.VisitDto;
import com.cgm.patient.entity.Patient;
import com.cgm.patient.entity.Visit;
import com.cgm.patient.entity.helper.VisitReason;
import com.cgm.patient.entity.helper.VisitType;
import com.cgm.patient.repository.VisitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {
    private VisitService visitService;

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        visitService = new VisitService(visitRepository, objectMapper);
    }

    @Test
    public void testCreateVisit() {
        VisitDto visitDto = new VisitDto(
                1L,
                VisitType.HOME,
                LocalDateTime.now(),
                VisitReason.FIRST_VISIT,
                "Family history"
        );

        when(objectMapper.convertValue(visitDto, Visit.class)).thenReturn(new Visit());
        when(visitRepository.save(any())).thenAnswer(invocation -> {
            Visit savedVisit = invocation.getArgument(0);
            savedVisit.setId(1L);
            return savedVisit;
        });

        Visit createdVisit = visitService.createVisit(visitDto);

        assertNotNull(createdVisit.getId());
    }

    @Test
    public void testGetAllVisits() {
        List<Visit> visits = new ArrayList<>();
        visits.add(new Visit(1L, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, "Family history", new Patient()));
        visits.add(new Visit(2L, VisitType.DOCTOR_OFFICE, LocalDateTime.now(), VisitReason.URGENT, null, new Patient()));

        when(visitRepository.findByPatientId(1L)).thenReturn(visits);

        List<Visit> result = visitService.getAllVisits(1L);

        assertEquals(visits, result);
    }

    @Test
    public void testGetVisitById() {
        Visit sampleVisit = new Visit(1L, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, "Family history", new Patient());

        when(visitRepository.findById(1L)).thenReturn(Optional.of(sampleVisit));
        when(visitRepository.findById(2L)).thenReturn(Optional.empty());

        Visit result = visitService.getVisitById(1L);

        assertEquals(sampleVisit, result);

        assertThrows(EntityNotFoundException.class, () -> visitService.getVisitById(2L));
    }

    @Test
    public void testUpdateVisit() {
        VisitDto visitDto = new VisitDto(
                1L,
                VisitType.HOME,
                LocalDateTime.now(),
                VisitReason.RECURRING_VISIT,
                "Updated family history"
        );

        Visit existingVisit = new Visit(1L, VisitType.DOCTOR_OFFICE, LocalDateTime.now(), VisitReason.FIRST_VISIT, "Updated family history", new Patient());

        when(visitRepository.findById(1L)).thenReturn(Optional.of(existingVisit));
        when(visitRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Visit updatedVisit = visitService.updateVisit(1L, visitDto);

        assertEquals(VisitType.HOME, updatedVisit.getVisitType());
        assertEquals(VisitReason.RECURRING_VISIT, updatedVisit.getReason());
        assertEquals("Updated family history", updatedVisit.getFamilyHistory());
    }
}

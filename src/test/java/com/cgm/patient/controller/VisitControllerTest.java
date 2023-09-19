package com.cgm.patient.controller;

import com.cgm.patient.dto.VisitDto;
import com.cgm.patient.entity.Patient;
import com.cgm.patient.entity.Visit;
import com.cgm.patient.entity.helper.VisitReason;
import com.cgm.patient.entity.helper.VisitType;
import com.cgm.patient.service.VisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VisitControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private VisitController visitController;

    @Mock
    private VisitService visitService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetAllVisits() throws Exception {
        List<Visit> visits = new ArrayList<>();
        visits.add(new Visit(1L, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, "Family history", new Patient()));
        visits.add(new Visit(2L, VisitType.DOCTOR_OFFICE, LocalDateTime.now(), VisitReason.URGENT, null, new Patient()));

        when(visitService.getAllVisits(1L)).thenReturn(visits);

        mockMvc.perform(get("/api/visits")
                        .param("patientId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].visitType").value("HOME"))
                .andExpect(jsonPath("$[1].reason").value("URGENT"));

        verify(visitService, times(1)).getAllVisits(1L);
    }

    @Test
    public void testCreateVisit() throws Exception {
        VisitDto visitDto = new VisitDto(
                1L,
                VisitType.HOME,
                LocalDateTime.now(),
                VisitReason.FIRST_VISIT,
                "Family history"
        );

        Visit createdVisit = new Visit(1L, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, "Family history", new Patient());

        when(visitService.createVisit(any())).thenReturn(createdVisit);

        mockMvc.perform(post("/api/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.visitType").value("HOME"))
                .andExpect(jsonPath("$.reason").value("FIRST_VISIT"));

        verify(visitService, times(1)).createVisit(any());
    }

    @Test
    public void testGetVisitById() throws Exception {
        Visit sampleVisit = new Visit(1L, VisitType.HOME, LocalDateTime.now(), VisitReason.FIRST_VISIT, "Family history", new Patient());

        when(visitService.getVisitById(1L)).thenReturn(sampleVisit);

        mockMvc.perform(get("/api/visits/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.visitType").value("HOME"))
                .andExpect(jsonPath("$.reason").value("FIRST_VISIT"));

        verify(visitService, times(1)).getVisitById(1L);
    }

    @Test
    public void testUpdateVisit() throws Exception {
        VisitDto visitDto = new VisitDto(
                1L,
                VisitType.HOME,
                LocalDateTime.now(),
                VisitReason.RECURRING_VISIT,
                "Updated family history"
        );

        Visit existingVisit = new Visit(1L, VisitType.DOCTOR_OFFICE, LocalDateTime.now(), VisitReason.FIRST_VISIT, "Family history", new Patient());

        when(visitService.updateVisit(1L, visitDto)).thenReturn(existingVisit);

        mockMvc.perform(put("/api/visits/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.visitType").value("DOCTOR_OFFICE"))
                .andExpect(jsonPath("$.reason").value("FIRST_VISIT"));

        verify(visitService, times(1)).updateVisit(1L, visitDto);
    }

}

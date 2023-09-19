package com.cgm.patient.controller;

import com.cgm.patient.dto.PatientDto;
import com.cgm.patient.entity.Patient;
import com.cgm.patient.service.PatientService;
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


public class PatientControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetAllPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "John", "Doe", LocalDateTime.of(1990, 1, 1, 0, 0, 0), "1234567890"));
        patients.add(new Patient(2L, "Alice", "Smith", LocalDateTime.of(1985, 5, 10, 0, 0, 0), "9876543210"));

        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient samplePatient = new Patient(1L, "John", "Doe", LocalDateTime.of(1990, 1, 1, 0, 0, 0), "1234567890");

        when(patientService.getPatientById(1L)).thenReturn(samplePatient);

        mockMvc.perform(get("/api/patients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(patientService, times(1)).getPatientById(1L);
    }

    @Test
    public void testCreatePatient() throws Exception {
        PatientDto patientDto = new PatientDto("John", "Doe", LocalDateTime.of(1990, 1, 1, 0, 0, 0), "1234567890");

        Patient createdPatient = new Patient(1L, "John", "Doe", LocalDateTime.of(1990, 1, 1, 0, 0, 0), "1234567890");

        when(patientService.createPatient(any())).thenReturn(createdPatient);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(patientService, times(1)).createPatient(any());
    }

    @Test
    public void testUpdatePatient() throws Exception {
        PatientDto patientDto = new PatientDto("Updated", "Patient", LocalDateTime.of(1995, 2, 15, 0, 0, 0), "9876543210");

        Patient updatedPatient = new Patient(1L, "Updated", "Patient", LocalDateTime.of(1995, 2, 15, 0, 0, 0), "9876543210");

        when(patientService.updatePatient(1L, patientDto)).thenReturn(updatedPatient);

        mockMvc.perform(put("/api/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Patient"));

        verify(patientService, times(1)).updatePatient(1L, patientDto);
    }


}

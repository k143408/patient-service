package com.cgm.patient.service;

import com.cgm.patient.dto.PatientDto;
import com.cgm.patient.entity.Patient;
import com.cgm.patient.repository.PatientRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {


    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository, objectMapper);
    }

    @Test
    public void testGetAllPatients() {
        List<Patient> patients = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        assertEquals(patients, result);
    }

    @Test
    public void testGetPatientById() {
        Long patientId = 1L;
        Patient patient = new Patient();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(patientId);

        assertEquals(patient, result);
    }

    @Test
    public void testGetPatientById_EntityNotFoundException() {
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> patientService.getPatientById(patientId));
    }

    @Test
    public void testCreatePatient() {
        PatientDto patientDto = new PatientDto("John", "Doe", LocalDateTime.now().minusDays(1), "1234567890");
        Patient patient = new Patient();
        when(objectMapper.convertValue(patientDto, Patient.class)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);

        Patient result = patientService.createPatient(patientDto);

        assertEquals(patient, result);
    }

    @Test
    public void testUpdatePatient() {
        Long patientId = 1L;
        PatientDto patientDto = new PatientDto("John", "Doe", LocalDateTime.now().minusDays(1), "1234567890");
        Patient existingPatient = new Patient();
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(existingPatient)).thenReturn(existingPatient);

        Patient result = patientService.updatePatient(patientId, patientDto);

        assertEquals(existingPatient, result);

        assertEquals(patientDto.dateOfBirth(), existingPatient.getDateOfBirth());
        assertEquals(patientDto.firstName(), existingPatient.getFirstName());
        assertEquals(patientDto.lastName(), existingPatient.getLastName());
        assertEquals(patientDto.socialSecurityNumber(), existingPatient.getSocialSecurityNumber());
    }
}

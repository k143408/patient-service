package com.cgm.patient.service;

import com.cgm.patient.dto.PatientDto;
import com.cgm.patient.entity.Patient;
import com.cgm.patient.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Patient is not found %d", id)));
    }

    @Transactional
    public Patient createPatient(PatientDto patientDto) {
        return patientRepository.save(convertToPatientEntity(patientDto));
    }


    private Patient convertToPatientEntity(PatientDto patientDto) {
        return objectMapper.convertValue(patientDto, Patient.class);
    }

    public Patient updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Patient is not found %d", id)));
        patient.setDateOfBirth(patientDto.dateOfBirth());
        patient.setFirstName(patientDto.firstName());
        patient.setLastName(patientDto.lastName());
        patient.setSocialSecurityNumber(patientDto.socialSecurityNumber());
        return patientRepository.save(patient);
    }

}

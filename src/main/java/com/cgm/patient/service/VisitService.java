package com.cgm.patient.service;

import com.cgm.patient.dto.VisitDto;
import com.cgm.patient.entity.Visit;
import com.cgm.patient.repository.VisitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Visit createVisit(VisitDto visit) {
        return visitRepository.save(convertToVisitEntity(visit));
    }

    private Visit convertToVisitEntity(VisitDto visit) {
        return objectMapper.convertValue(visit, Visit.class);
    }

    public List<Visit> getAllVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    public Visit getVisitById(Long id) {
        return visitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Visit is not found %d", id)));
    }

    @Transactional
    public Visit updateVisit(Long id, VisitDto visitDto) {
        Visit visitById = getVisitById(id);
        visitById.setVisitType(visitDto.visitType());
        visitById.setReason(visitDto.reason());
        visitById.setVisitType(visitDto.visitType());
        visitById.setDateTime(visitDto.dateTime());
        visitById.setFamilyHistory(visitById.getFamilyHistory());
        return visitRepository.save(visitById);
    }
}

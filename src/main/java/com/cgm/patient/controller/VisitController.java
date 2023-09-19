package com.cgm.patient.controller;

import com.cgm.patient.dto.VisitDto;
import com.cgm.patient.entity.Visit;
import com.cgm.patient.service.VisitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@Validated
@Tag(name = "Visits", description = "Operations related to visits")
public class VisitController {

    private final VisitService visitService;

    @GetMapping
    public List<Visit> getAllVisits(@RequestParam Long patientId) {
        return visitService.getAllVisits(patientId);
    }

    @PostMapping
    public ResponseEntity<Visit> createVisit(@RequestBody @Valid VisitDto visit) {
        Visit savedVisit = visitService.createVisit(visit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVisit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable Long id) {
        Visit visit = visitService.getVisitById(id);
        return ResponseEntity.ok(visit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable Long id, @RequestBody @Valid VisitDto visitDto) {
        Visit updatedVisit = visitService.updateVisit(id, visitDto);
        return ResponseEntity.ok(updatedVisit);
    }
}

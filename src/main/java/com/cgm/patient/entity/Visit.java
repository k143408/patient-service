package com.cgm.patient.entity;

import com.cgm.patient.entity.helper.VisitReason;
import com.cgm.patient.entity.helper.VisitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VisitType visitType;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private VisitReason reason;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String familyHistory;

    @ManyToOne
    private Patient patient;

}

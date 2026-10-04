package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "expositions")
@Getter
@Setter
public class ExpositionEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "musee_id", nullable = false)
    private MuseeEntity musee;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String commissaire;

    private String lieu;

    private boolean expositionPermanente;

    /*
     * Référence vers ms-media
     */
    private UUID imageMediaId;
}
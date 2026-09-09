package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "circuits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CircuitEntity extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TypeCircuit type;

    @Column(name = "zone_touristique_id", nullable = false)
    private UUID zoneTouristiqueId;

    @Column(name = "niveau_difficulte", length = 20)
    @Enumerated(EnumType.STRING)
    private NiveauDifficulte niveauDifficulte;

    @Column(nullable = false)
    private boolean actif = true;

    @Column(name = "statut", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private StatutCircuit statut = StatutCircuit.BROUILLON;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;
}
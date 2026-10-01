package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.CategorieOffre;
import com.pgvpt.enums.StatutOffre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "offres_touristiques",
        indexes = {
                @Index(name = "idx_offre_zone", columnList = "zone_touristique_id"),
                @Index(name = "idx_offre_acteur", columnList = "acteur_id"),
                @Index(name = "idx_offre_circuit", columnList = "circuit_id"),
                @Index(name = "idx_offre_categorie", columnList = "categorie"),
                @Index(name = "idx_offre_statut", columnList = "statut")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OffreTouristiqueEntity extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategorieOffre categorie;

    @Column(name = "zone_touristique_id", nullable = false)
    private UUID zoneTouristiqueId;

    @Column(name = "acteur_id", nullable = false)
    private UUID acteurId;

    @Column(name = "circuit_id")
    private UUID circuitId;

    @Column(nullable = false)
    private BigDecimal prix;

    @Column(nullable = false, length = 3)
    private String devise = "XOF";

    @Column(name = "duree_minutes")
    private Integer dureeMinutes;

    @Column(name = "capacite_max")
    private Integer capaciteMax;

    @Column(name = "date_debut_validite")
    private LocalDate dateDebutValidite;

    @Column(name = "date_fin_validite")
    private LocalDate dateFinValidite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutOffre statut = StatutOffre.BROUILLON;

    @Column(nullable = false)
    private boolean actif = true;
}

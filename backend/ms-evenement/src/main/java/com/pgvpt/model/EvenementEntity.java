package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.StatutEvenement;
import com.pgvpt.enums.TypeEvenement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "evenements",
        indexes = {
                @Index(
                        name = "idx_evenement_zone",
                        columnList = "zone_touristique_id"
                ),
                @Index(
                        name = "idx_evenement_type",
                        columnList = "type"
                ),
                @Index(
                        name = "idx_evenement_statut",
                        columnList = "statut"
                ),
                @Index(
                        name = "idx_evenement_organisateur",
                        columnList = "organisateur_acteur_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvenementEntity extends BaseEntity {

    @Column(nullable = false, length = 250)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TypeEvenement type;

    @Column(name = "zone_touristique_id", nullable = false)
    private UUID zoneTouristiqueId;

    @Column(name = "patrimoine_id")
    private UUID patrimoineId;

    @Column(name = "organisateur_acteur_id", nullable = false)
    private UUID organisateurActeurId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutEvenement statut = StatutEvenement.BROUILLON;

    @Column(name = "capacite_max")
    private Integer capaciteMax;

    @Column(nullable = false)
    private Double tarif = 0.0;

    @Column(nullable = false, length = 3)
    private String devise = "XOF";

    @Column(name = "reservation_requise", nullable = false)
    private boolean reservationRequise = false;

    @ElementCollection
    @CollectionTable(name = "evenement_langues", joinColumns = @JoinColumn(name = "evenement_id"))
    @Column(name = "langue")
    private Set<String> langues = new HashSet<>();
}
package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Entity
@Table(name = "etapes_circuit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtapeCircuitEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "circuit_id", nullable = false)
    private CircuitEntity circuit;

    @Column(name = "patrimoine_id", nullable = false)
    private UUID patrimoineId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activite_id", nullable = false)
    private ActiviteEntity activite;

    @Column(nullable = false)
    private Integer ordre;

    @Column(name = "duree_minutes")
    private Integer dureeMinutes;

    @Column(name = "distance_depuis_precedente_km")
    private Double distanceDepuisPrecedenteKm;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false, length = 3)
    private String devise = "XOF";

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean obligatoire = true;

    @Column(nullable = false)
    private boolean actif = true;
}
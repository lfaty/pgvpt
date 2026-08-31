package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.TypePointAcces;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "points_acces")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointAccesEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "patrimoine_id", nullable = false)
    private UUID patrimoineId;

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePointAcces type;

    private String description;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String adresse;

    private Double distancePatrimoineMetres;

    private Boolean accessiblePMR;

    private Boolean parkingDisponible;

    private Boolean transportPublic;

    private String horaires;

}

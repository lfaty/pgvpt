package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.TypePointAcceMetier;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "points_acces")
@Getter
@Setter
public class PointAccesEntity extends BaseEntity {
    @Column(name = "patrimoine_id", nullable = false)
    private UUID patrimoineId;

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePointAcceMetier type;

    private String description;
    private Double latitude;
    private Double longitude;
    private String adresse;

    @Column(name = "distance_patrimoine_metres")
    private Double distancePatrimoineMetres;

    @Column(name = "accessible_pmr")
    private Boolean accessiblePMR;

    @Column(name = "parking_disponible")
    private Boolean parkingDisponible;

    @Column(name = "transport_public")
    private Boolean transportPublic;

    private String horaires;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point geom;

}

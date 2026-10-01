package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "geolocalisations")
@Getter
@Setter
public class GeolocalisationEntity extends BaseEntity {
    private Double latitude;
    private Double longitude;

    private Double altitude;

    @Column(name = "precision_metres")
    private  Double precisionMetres;
    private  String adresse;

    @Column(name = "lieu_dit")
    private String lieuDit;
    private String repere;

    @Column(name = "systeme_reference")
    private String systemeReference = "WGS84";

    @Column(name = "code_epsg")
    private Integer codeEpsg = 4326;

    @Enumerated(EnumType.STRING)
    private SourceDonneeMetier source;

    @Column(name = "date_acquisition")
    private LocalDate dateAcquisition;

    @Enumerated(EnumType.STRING)
    @Column(name = "methode_acquisition")
    private MethodeAcquisitionMetier methodeAcquisition;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_fiabilite")
    private NiveauFiabiliteMetier niveauFiabilite;
    private UUID patrimoineId = null;
    private UUID entrepriseId = null;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zone_touristique_id", nullable = false)
    private ZoneTouristiqueEntity zoneTouristique = null;

    @ManyToOne
    @JoinColumn(name = "village_id")
    private  VillageEntity village= null;

    @ManyToOne
    @JoinColumn(name = "quartier_id")
    private QuartierEntity quartier = null;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point geom;
}

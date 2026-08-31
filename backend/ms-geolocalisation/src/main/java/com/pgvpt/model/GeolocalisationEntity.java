package com.pgvpt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "geolocalisations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeolocalisationEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private @Nullable UUID patrimoineId = null;

    private @Nullable UUID entrepriseId = null;

    private Double latitude;

    private Double longitude;

    private @Nullable Double altitude;

    private @Nullable Double precisionMetres;

    private @Nullable String adresse;

    private @Nullable String lieuDit;

    private @Nullable String repere;

    private String systemeReference = "WGS84";

    private Integer codeEpsg = 4326;

    private @Nullable SourceEnum source;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private @Nullable LocalDate dateAcquisition;

    private @Nullable MethodeAcquisition methodeAcquisition;

    private @Nullable NiveauFiabilite niveauFiabilite;

    @JoinColumn(name = "zoneTouristique_id", nullable = false, unique = true)
    @ManyToOne
    private ZoneGeographiqueEntity zoneTouristique;

    @JoinColumn(name = "village_id", unique = true)
    @ManyToOne
    private @Nullable VillageEntity village= null;


    @JoinColumn(name = "quartier_id", unique = true)
    @ManyToOne
    private @Nullable QuartierEntity quartier = null;


}

package com.pgvpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.TypeZone;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "zones_touristiques")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTouristiqueEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String nom;

    private @Nullable String description;

    private @Nullable TypeZone typeZone;

    private @Nullable Double superficieKm2;

    private @Nullable Double latitudeCentre;

    private @Nullable Double longitudeCentre;

    @Builder.Default
    private Boolean actif = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zone_geographique_id", nullable = false)
    private ZoneGeographiqueEntity zoneGeographique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private @Nullable DepartementEntity departement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id")
    private @Nullable CommuneEntity commune;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id")
    private @Nullable VillageEntity village;


}


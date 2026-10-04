package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.TypeZoneMetier;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.UUID;


@Entity
@Table(name = "zones_touristiques")
@Getter
@Setter
public class ZoneTouristiqueEntity extends BaseEntity {
    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String nom;

    private @Nullable String description;

    private @Nullable TypeZoneMetier typeZone;

    private @Nullable Double superficieKm2;

    private @Nullable Double latitudeCentre;

    private @Nullable Double longitudeCentre;

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


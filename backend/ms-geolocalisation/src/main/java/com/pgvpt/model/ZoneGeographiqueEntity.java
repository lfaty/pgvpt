package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.*;

@Entity
@Table(name = "zones_geographiques")
@Getter
@Setter
public class ZoneGeographiqueEntity extends BaseEntity {
    private String code;

    private String nom;

    private @Nullable String description;

    private @Nullable Double superficieKm2;

    private @Nullable Double latitudeCentre;

    private @Nullable Double longitudeCentre;

    private Boolean actif = true;

    @OneToMany(mappedBy = "zoneGeographique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZoneGeographiqueRegionEntity> regionLinks ;

}

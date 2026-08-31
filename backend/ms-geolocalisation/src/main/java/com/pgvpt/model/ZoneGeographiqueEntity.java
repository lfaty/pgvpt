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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneGeographiqueEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    private String nom;

    private @Nullable String description;

    private @Nullable Double superficieKm2;

    private @Nullable Double latitudeCentre;

    private @Nullable Double longitudeCentre;

    private Boolean actif = true;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "zone_geographique_region",
            joinColumns = @JoinColumn(name = "zone_geographique_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id"),
            uniqueConstraints = {
                    @UniqueConstraint(name = "uk_zone_geo_region_id", columnNames = {"region_id"})
            }
    )
    private List<RegionEntity> regions = new ArrayList<>();

}


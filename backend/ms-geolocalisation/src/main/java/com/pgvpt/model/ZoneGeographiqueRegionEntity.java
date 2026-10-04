package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "zone_geographique_region",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_zgr_region_id", columnNames = {"region_id"})
    }
)
@Getter
@Setter
public class ZoneGeographiqueRegionEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zone_geographique_id", nullable = false)
    private ZoneGeographiqueEntity zoneGeographique;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;
}

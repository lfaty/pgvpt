package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entité de jointure entre ZoneGeographiqueEntity et RegionEntity.
 * Étend BaseEntity pour disposer de createdAt, updatedAt et id (via @GeneratedValue).
 * Contrainte : une région ne peut appartenir qu'à une seule zone géographique.
 */
@Entity
@Table(
    name = "zone_geographique_region",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_zgr_region_id", columnNames = {"region_id"})
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneGeographiqueRegionEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zone_geographique_id", nullable = false)
    private ZoneGeographiqueEntity zoneGeographique;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;
}

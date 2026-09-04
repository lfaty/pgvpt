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

    @Builder.Default
    private Boolean actif = true;

    /**
     * Relation vers l'entité de jointure ZoneGeographiqueRegionEntity.
     * Remplace l'ancien @ManyToMany pour exposer id, createdAt, updatedAt sur chaque lien zone-région.
     */
    @OneToMany(mappedBy = "zoneGeographique", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ZoneGeographiqueRegionEntity> regionLinks = new ArrayList<>();

}

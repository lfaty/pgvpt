package com.pgvpt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "regions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionEntity extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String nom;

    private @Nullable String codePostal;

    private @Nullable String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pays_id", nullable = false)
    private PaysEntity paysEntity;


    /**
     * Relation inverse vers les entités de jointure.
     * Permet de savoir à quelle(s) ZoneGeographique une région est rattachée.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "region")
    private List<ZoneGeographiqueRegionEntity> zoneLinks = new ArrayList<>();


}


package com.pgvpt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "regions")
@Getter
@Setter
public class RegionEntity extends BaseEntity {
    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String nom;

    private @Nullable String codePostal;

    private @Nullable String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pays_id", nullable = false)
    private PaysEntity paysEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "region")
    private List<ZoneGeographiqueRegionEntity> zoneLinks;


}


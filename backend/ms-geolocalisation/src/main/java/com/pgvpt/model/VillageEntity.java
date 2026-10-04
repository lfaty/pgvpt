package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "villages")
@Getter
@Setter
public class VillageEntity extends BaseEntity {
    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String nom;

    private @Nullable String description;

    @ManyToOne
    @JoinColumn(name = "commune_id")
    private CommuneEntity commune;

//    @OneToMany
//    private List<QuartierReference> quartierReferences;

}


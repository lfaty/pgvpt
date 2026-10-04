package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "quartiers")
@Getter
@Setter
public class QuartierEntity extends BaseEntity {
    private String code;

    @Column(unique = true)
    private String nom;

    private @Nullable String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id", nullable = false)
    private CommuneEntity commune;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "village_id", nullable = false)
    private VillageEntity village;

}


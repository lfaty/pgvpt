package com.pgvpt.model;

import com.dev.pgvpt.api.model.CommuneReference;
import com.dev.pgvpt.api.model.VillageReference;
import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Entity
@Table(name = "quartiers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartierEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    @Column(unique = true)
    private String nom;

    private @Nullable String description;

    @ManyToOne
    @JoinColumn(name = "commune_id")
    private CommuneEntity commune;

    @ManyToOne
    @JoinColumn(name = "village_id")
    private VillageEntity village;

}


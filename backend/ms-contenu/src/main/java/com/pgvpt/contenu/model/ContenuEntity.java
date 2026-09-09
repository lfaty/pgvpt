package com.pgvpt.contenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.pgvpt.contenu.common.BaseEntity;
import com.pgvpt.contenu.enums.Statut;

import java.util.UUID;

@Entity
@Table(name="contenus")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContenuEntity extends BaseEntity {
    private String type;
    private String langue;
    private String titre;
    private String resume;
    private String corps;
    private UUID patrimoineId;
    private UUID auteurActeurId;
    private String motsCles;
    private Statut statut;
}

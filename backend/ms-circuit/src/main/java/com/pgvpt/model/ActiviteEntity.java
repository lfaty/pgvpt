package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "activites")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiviteEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 150)
    private String libelle;

    @Column(length = 1000)
    private String description;

    @Column(name = "prix_initial", nullable = false)
    private Double prixInitial;

    @Column(nullable = false)
    private boolean actif = true;
}

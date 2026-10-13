package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.TypeEspeceMetier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "espece_protegees")
@Getter
@Setter
public class EspeceProtegeeEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_naturel_id", nullable = false)
    private SiteNaturelEntity siteNaturel;

    @Column(columnDefinition = "TEXT")
    private String nomCommun;
    @Column(columnDefinition = "TEXT")
    private String nomScientifique;
    @Column(columnDefinition = "TEXT")
    private String nomLocal;

    @Enumerated(EnumType.STRING)
    private TypeEspeceMetier type;

    @Column(columnDefinition = "TEXT")
    private String statutConservation;
    @Column(columnDefinition = "TEXT")
    private String niveauProtection;
    @Column(columnDefinition = "TEXT")
    private String description;

    /*
     * Référence vers ms-media.
     */
    @Column(name = "photo_media_id")
    private UUID photoMediaId;
}
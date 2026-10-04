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

    private String nomCommun;
    private String nomScientifique;
    private String nomLocal;

    @Enumerated(EnumType.STRING)
    private TypeEspeceMetier type;

    private String statutConservation;
    private String niveauProtection;
    private String description;

    /*
     * Référence vers ms-media.
     */
    @Column(name = "photo_media_id")
    private UUID photoMediaId;
}
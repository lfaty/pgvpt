package com.pgvpt.contenu.model;

import com.pgvpt.contenu.enums.TypeContenuMetier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.pgvpt.contenu.common.BaseEntity;
import com.pgvpt.contenu.enums.StatutContenuMetier;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="contenus")
@Getter @Setter
public class ContenuEntity extends BaseEntity {
    @Column(name = "patrimoine_id", nullable = false)
    private UUID patrimoineId;

    @Enumerated(EnumType.STRING)
    private TypeContenuMetier type;

    private String langue;
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String resume;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String corps;

    @Column(name = "auteur_acteur_id")
    private UUID auteurActeurId;

    @ElementCollection
    @CollectionTable(name = "contenu_mots_cles", joinColumns = @JoinColumn(name = "contenu_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "mot_cle")
    private List<String> motsCles;

    @ElementCollection
    @CollectionTable(name = "contenu_media_ids", joinColumns = @JoinColumn(name = "contenu_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "media_id")
    private List<UUID> mediaIds;

    @Enumerated(EnumType.STRING)
    private StatutContenuMetier statut = StatutContenuMetier.BROUILLON;

    @Column(name = "date_publication")
    private LocalDateTime datePublication;

}

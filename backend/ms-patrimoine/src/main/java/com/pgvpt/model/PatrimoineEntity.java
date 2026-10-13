package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.embeddable.AccessibiliteEmbeddable;
import com.pgvpt.embeddable.ContactPatrimoineEmbeddable;
import com.pgvpt.embeddable.HoraireOuvertureEmbeddable;
import com.pgvpt.embeddable.MenaceEmbeddable;
import com.pgvpt.embeddable.PhotoPrincipale;
import com.pgvpt.enums.*;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patrimoines")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_patrimoine", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class PatrimoineEntity extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String code;
    @Column(columnDefinition = "TEXT")
    private String nom;
    @Column(columnDefinition = "TEXT")
    private String nomLocal;
    @Column(columnDefinition = "TEXT")
    private String nomHistorique;

    @Enumerated(EnumType.STRING)
    private TypePatrimoineMetier type;

    @Enumerated(EnumType.STRING)
    private CategoriePatrimoineMetier categorie;
    @Column(columnDefinition = "TEXT")
    private String sousCategorie;

    @Enumerated(EnumType.STRING)
    private PeriodeHistoriqueMetier periode;
    @Column(columnDefinition = "TEXT")
    private String siecle;

    private LocalDate dateOuverture;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String descriptionCourte;

    @Column(columnDefinition = "TEXT")
    private String historique;

    @Column(columnDefinition = "TEXT")
    private String importanceHistorique;

    @Column(columnDefinition = "TEXT")
    private String importanceCulturelle;

    @Column(columnDefinition = "TEXT")
    private String importanceTouristique;

    @Column(columnDefinition = "TEXT")
    private String valeurPatrimoniale;

    @Column(columnDefinition = "TEXT")
    private String valeurSpirituelle;

    @ElementCollection
    @CollectionTable(name = "patrimoine_langues", joinColumns = @JoinColumn(name = "patrimoine_id"))
    @Column(name = "langue", columnDefinition = "TEXT")
    private List<String> langues;

    @ElementCollection
    @CollectionTable(name = "patrimoine_traditions", joinColumns = @JoinColumn(name = "patrimoine_id"))
    @Column(name = "tradition", columnDefinition = "TEXT")
    private List<String> traditionsAssociees;

    @Enumerated(EnumType.STRING)
    private StatutPatrimoineMetier statut = StatutPatrimoineMetier.BROUILLON;

    @Column(nullable = false)
    private boolean classePatrimoine;

    @Column(columnDefinition = "TEXT")
    private String referenceClassement;

    private LocalDate dateClassement;

    @Column(nullable = false)
    private boolean inscritUnesco;

    @Column(columnDefinition = "TEXT")
    private String nomSiteUnesco;

    private LocalDate dateInscriptionUnesco;

    @Embedded
    private PhotoPrincipale photoPrincipale;

    @Column(columnDefinition = "TEXT")
    private String protectionJuridique;

    @Column(columnDefinition = "TEXT")
    private String organismeGestionnaire;

    @Column(columnDefinition = "TEXT")
    private String proprietaire;
    @Column(columnDefinition = "TEXT")
    private String gestionnaire;

    @Embedded
    private ContactPatrimoineEmbeddable contact;

    @Embedded
    private AccessibiliteEmbeddable accessibilite;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "patrimoine_horaires", joinColumns = @JoinColumn(name = "patrimoine_id"))
    private List<HoraireOuvertureEmbeddable> horaires = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "patrimoine_menaces", joinColumns = @JoinColumn(name = "patrimoine_id"))
    private List<MenaceEmbeddable> menaces = new ArrayList<>();

    @Version
    private Long version;

    private Instant publishedAt;

    @OneToOne(mappedBy = "patrimoine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ConservationEntity conservation;

}

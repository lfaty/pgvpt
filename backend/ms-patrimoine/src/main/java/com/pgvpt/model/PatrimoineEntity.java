package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.embeddable.AccessibiliteEmbeddable;
import com.pgvpt.embeddable.ContactPatrimoineEmbeddable;
import com.pgvpt.embeddable.HoraireOuvertureEmbeddable;
import com.pgvpt.embeddable.MenaceEmbeddable;
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
    private String code;
    private String nom;
    private String nomLocal;
    private String nomHistorique;

    @Enumerated(EnumType.STRING)
    private TypePatrimoineMetier type;

    @Enumerated(EnumType.STRING)
    private CategoriePatrimoineMetier categorie;
    private String sousCategorie;

    @Enumerated(EnumType.STRING)
    private PeriodeHistoriqueMetier periode;
    private String siecle;

    private LocalDate dateOuverture;
    private String description;
    private String descriptionCourte;
    private String historique;
    private String importanceHistorique;
    private String importanceCulturelle;
    private String importanceTouristique;
    private String valeurPatrimoniale;
    private String valeurSpirituelle;

    @ElementCollection
    @CollectionTable(name = "patrimoine_langues", joinColumns = @JoinColumn(name = "patrimoine_id"))
    private List<String> langues;

    @ElementCollection
    @CollectionTable(name = "patrimoine_traditions", joinColumns = @JoinColumn(name = "patrimoine_id"))
    private List<String> traditionsAssociees;


    @Enumerated(EnumType.STRING)
    private StatutPatrimoineMetier statut = StatutPatrimoineMetier.BROUILLON;

    @Column(nullable = false)
    private boolean classePatrimoine;

    private String referenceClassement;

    private LocalDate dateClassement;

    @Column(nullable = false)
    private boolean inscritUnesco;

    private String nomSiteUnesco;

    private LocalDate dateInscriptionUnesco;

    private String protectionJuridique;
    private String organismeGestionnaire;

    private String proprietaire;
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

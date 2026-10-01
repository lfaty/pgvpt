package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.EtatConservationMetier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(
        name = "conservations",
        uniqueConstraints = {@UniqueConstraint(name = "uk_conservation_patrimoine", columnNames = "patrimoine_id")}
)
@Getter
@Setter
public class ConservationEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patrimoine_id", nullable = false, unique = true)
    private PatrimoineEntity patrimoine;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_conservateur_id", nullable = false)
    private ContactConservateurEntity contactConservateur;

    @Enumerated(EnumType.STRING)
    private EtatConservationMetier etat;
    private LocalDate dateEvaluation;
    private String descriptionEtat;

    @ElementCollection
    @CollectionTable(name = "conservation_degradation", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "degradation")
    private List<String> degradations ;

    @ElementCollection
    @CollectionTable(name = "conservation_cause_degradation", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "cause")
    private List<String> causesDegradation ;

    @ElementCollection
    @CollectionTable(name = "conservation_travaux", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "travail")
    private List<String> travauxNecessaires ;

    private String dernierTravaux;
    private LocalDate dateDerniersTravaux;
    private String organismeConservation;
    private BigDecimal budgetEstime;

    @ElementCollection
    @CollectionTable(name = "conservation_recommandation", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "recommandation")
    private List<String> recommandations;
}
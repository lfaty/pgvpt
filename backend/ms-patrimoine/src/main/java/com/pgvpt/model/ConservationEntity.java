package com.pgvpt.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conservations")
public class ConservationEntity {

    public ConservationEntity() {}

    public ConservationEntity(UUID id, String etat, LocalDate dateEvaluation, String evaluateur, String descriptionEtat, List<String> degradations, List<String> causesDegradation, List<String> travauxNecessaires, String dernierTravaux, LocalDate dateDerniersTravaux, String organismeConservation, BigDecimal budgetEstime, List<String> recommandations) {
        this.id = id;
        this.etat = etat;
        this.dateEvaluation = dateEvaluation;
        this.evaluateur = evaluateur;
        this.descriptionEtat = descriptionEtat;
        this.degradations = degradations;
        this.causesDegradation = causesDegradation;
        this.travauxNecessaires = travauxNecessaires;
        this.dernierTravaux = dernierTravaux;
        this.dateDerniersTravaux = dateDerniersTravaux;
        this.organismeConservation = organismeConservation;
        this.budgetEstime = budgetEstime;
        this.recommandations = recommandations;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String etat;
    private LocalDate dateEvaluation;
    private String evaluateur;
    @Column(columnDefinition = "TEXT")
    private String descriptionEtat;
    @ElementCollection
    @CollectionTable(name = "conservation_degradations", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "degradation")
    private List<String> degradations = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "conservation_causes", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "cause")
    private List<String> causesDegradation = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "conservation_travaux", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "travail")
    private List<String> travauxNecessaires = new ArrayList<>();
    @Column(columnDefinition = "TEXT")
    private String dernierTravaux;
    private LocalDate dateDerniersTravaux;
    private String organismeConservation;
    private BigDecimal budgetEstime;
    @ElementCollection
    @CollectionTable(name = "conservation_recommandations", joinColumns = @JoinColumn(name = "conservation_id"))
    @Column(name = "recommandation")
    private List<String> recommandations = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    public LocalDate getDateEvaluation() { return dateEvaluation; }
    public void setDateEvaluation(LocalDate dateEvaluation) { this.dateEvaluation = dateEvaluation; }

    public String getEvaluateur() { return evaluateur; }
    public void setEvaluateur(String evaluateur) { this.evaluateur = evaluateur; }

    public String getDescriptionEtat() { return descriptionEtat; }
    public void setDescriptionEtat(String descriptionEtat) { this.descriptionEtat = descriptionEtat; }

    public List<String> getDegradations() { return degradations; }
    public void setDegradations(List<String> degradations) { this.degradations = degradations; }

    public List<String> getCausesDegradation() { return causesDegradation; }
    public void setCausesDegradation(List<String> causesDegradation) { this.causesDegradation = causesDegradation; }

    public List<String> getTravauxNecessaires() { return travauxNecessaires; }
    public void setTravauxNecessaires(List<String> travauxNecessaires) { this.travauxNecessaires = travauxNecessaires; }

    public String getDernierTravaux() { return dernierTravaux; }
    public void setDernierTravaux(String dernierTravaux) { this.dernierTravaux = dernierTravaux; }

    public LocalDate getDateDerniersTravaux() { return dateDerniersTravaux; }
    public void setDateDerniersTravaux(LocalDate dateDerniersTravaux) { this.dateDerniersTravaux = dateDerniersTravaux; }

    public String getOrganismeConservation() { return organismeConservation; }
    public void setOrganismeConservation(String organismeConservation) { this.organismeConservation = organismeConservation; }

    public BigDecimal getBudgetEstime() { return budgetEstime; }
    public void setBudgetEstime(BigDecimal budgetEstime) { this.budgetEstime = budgetEstime; }

    public List<String> getRecommandations() { return recommandations; }
    public void setRecommandations(List<String> recommandations) { this.recommandations = recommandations; }
}

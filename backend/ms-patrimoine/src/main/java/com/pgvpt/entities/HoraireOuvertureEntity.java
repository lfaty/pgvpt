package com.pgvpt.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "horaires_ouverture")
public class HoraireOuvertureEntity {
    
    public HoraireOuvertureEntity() {}

    public HoraireOuvertureEntity(UUID id, String jour, Boolean ouvert, java.time.LocalTime heureOuverture, java.time.LocalTime heureFermeture, java.time.LocalTime pauseDebut, java.time.LocalTime pauseFin, Boolean surReservation, String commentaire, PatrimoineEntity patrimoine) {
        this.id = id;
        this.jour = jour;
        this.ouvert = ouvert;
        this.heureOuverture = heureOuverture;
        this.heureFermeture = heureFermeture;
        this.pauseDebut = pauseDebut;
        this.pauseFin = pauseFin;
        this.surReservation = surReservation;
        this.commentaire = commentaire;
        this.patrimoine = patrimoine;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String jour;
    private Boolean ouvert;
    private java.time.LocalTime heureOuverture;
    private java.time.LocalTime heureFermeture;
    private java.time.LocalTime pauseDebut;
    private java.time.LocalTime pauseFin;
    private Boolean surReservation;
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "patrimoine_id", nullable = false)
    private PatrimoineEntity patrimoine;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getJour() { return jour; }
    public void setJour(String jour) { this.jour = jour; }

    public Boolean getOuvert() { return ouvert; }
    public void setOuvert(Boolean ouvert) { this.ouvert = ouvert; }

    public java.time.LocalTime getHeureOuverture() { return heureOuverture; }
    public void setHeureOuverture(java.time.LocalTime heureOuverture) { this.heureOuverture = heureOuverture; }

    public java.time.LocalTime getHeureFermeture() { return heureFermeture; }
    public void setHeureFermeture(java.time.LocalTime heureFermeture) { this.heureFermeture = heureFermeture; }

    public java.time.LocalTime getPauseDebut() { return pauseDebut; }
    public void setPauseDebut(java.time.LocalTime pauseDebut) { this.pauseDebut = pauseDebut; }

    public java.time.LocalTime getPauseFin() { return pauseFin; }
    public void setPauseFin(java.time.LocalTime pauseFin) { this.pauseFin = pauseFin; }

    public Boolean getSurReservation() { return surReservation; }
    public void setSurReservation(Boolean surReservation) { this.surReservation = surReservation; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public PatrimoineEntity getPatrimoine() { return patrimoine; }
    public void setPatrimoine(PatrimoineEntity patrimoine) { this.patrimoine = patrimoine; }
}

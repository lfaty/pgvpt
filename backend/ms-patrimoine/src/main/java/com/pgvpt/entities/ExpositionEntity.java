package com.pgvpt.entities;

import jakarta.persistence.Column;
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

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "expositions")
public class ExpositionEntity {

    public ExpositionEntity() {}

    public ExpositionEntity(UUID id, String titre, String description, LocalDate dateDebut, LocalDate dateFin, String commissaire, String lieu, Boolean expositionPermanente, String imageUrl, MuseeEntity musee) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commissaire = commissaire;
        this.lieu = lieu;
        this.expositionPermanente = expositionPermanente;
        this.imageUrl = imageUrl;
        this.musee = musee;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String titre;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String commissaire;
    private String lieu;
    private Boolean expositionPermanente;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "musee_id", nullable = false)
    private MuseeEntity musee;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getCommissaire() { return commissaire; }
    public void setCommissaire(String commissaire) { this.commissaire = commissaire; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public Boolean getExpositionPermanente() { return expositionPermanente; }
    public void setExpositionPermanente(Boolean expositionPermanente) { this.expositionPermanente = expositionPermanente; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public MuseeEntity getMusee() { return musee; }
    public void setMusee(MuseeEntity musee) { this.musee = musee; }
}

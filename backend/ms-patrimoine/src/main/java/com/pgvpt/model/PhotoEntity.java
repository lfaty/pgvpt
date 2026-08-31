package com.pgvpt.model;

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
@Table(name = "photos")
public class PhotoEntity {

    public PhotoEntity() {}

    public PhotoEntity(UUID id, String url, String miniatureUrl, String titre, String description, String auteur, LocalDate datePrise, String droits, String licence, Boolean photoPrincipale, Integer ordre, PatrimoineEntity patrimoine) {
        this.id = id;
        this.url = url;
        this.miniatureUrl = miniatureUrl;
        this.titre = titre;
        this.description = description;
        this.auteur = auteur;
        this.datePrise = datePrise;
        this.droits = droits;
        this.licence = licence;
        this.photoPrincipale = photoPrincipale;
        this.ordre = ordre;
        this.patrimoine = patrimoine;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String url;
    private String miniatureUrl;
    private String titre;
    private String description;
    private String auteur;
    private LocalDate datePrise;
    private String droits;
    private String licence;
    private Boolean photoPrincipale;
    private Integer ordre;

    @ManyToOne
    @JoinColumn(name = "patrimoine_id", nullable = false)
    private PatrimoineEntity patrimoine;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getMiniatureUrl() { return miniatureUrl; }
    public void setMiniatureUrl(String miniatureUrl) { this.miniatureUrl = miniatureUrl; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public LocalDate getDatePrise() { return datePrise; }
    public void setDatePrise(LocalDate datePrise) { this.datePrise = datePrise; }

    public String getDroits() { return droits; }
    public void setDroits(String droits) { this.droits = droits; }

    public String getLicence() { return licence; }
    public void setLicence(String licence) { this.licence = licence; }

    public Boolean getPhotoPrincipale() { return photoPrincipale; }
    public void setPhotoPrincipale(Boolean photoPrincipale) { this.photoPrincipale = photoPrincipale; }

    public Integer getOrdre() { return ordre; }
    public void setOrdre(Integer ordre) { this.ordre = ordre; }

    public PatrimoineEntity getPatrimoine() { return patrimoine; }
    public void setPatrimoine(PatrimoineEntity patrimoine) { this.patrimoine = patrimoine; }
}

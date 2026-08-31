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

import java.util.UUID;

@Entity
@Table(name = "medias")
public class MediaEntity {

    public MediaEntity() {}

    public MediaEntity(UUID id, String type, String titre, String description, String url, String miniatureUrl, Integer duree, String auteur, String licence, PatrimoineEntity patrimoine) {
        this.id = id;
        this.type = type;
        this.titre = titre;
        this.description = description;
        this.url = url;
        this.miniatureUrl = miniatureUrl;
        this.duree = duree;
        this.auteur = auteur;
        this.licence = licence;
        this.patrimoine = patrimoine;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;
    private String titre;
    private String description;
    private String url;
    private String miniatureUrl;
    private Integer duree;
    private String auteur;
    private String licence;

    @ManyToOne
    @JoinColumn(name = "patrimoine_id", nullable = false)
    private PatrimoineEntity patrimoine;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getMiniatureUrl() { return miniatureUrl; }
    public void setMiniatureUrl(String miniatureUrl) { this.miniatureUrl = miniatureUrl; }

    public Integer getDuree() { return duree; }
    public void setDuree(Integer duree) { this.duree = duree; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getLicence() { return licence; }
    public void setLicence(String licence) { this.licence = licence; }

    public PatrimoineEntity getPatrimoine() { return patrimoine; }
    public void setPatrimoine(PatrimoineEntity patrimoine) { this.patrimoine = patrimoine; }
}

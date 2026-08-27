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

import java.util.UUID;

@Entity
@Table(name = "especes_protegees")
public class EspeceProtegeeEntity {

    public EspeceProtegeeEntity() {}

    public EspeceProtegeeEntity(UUID id, String nomCommun, String nomScientifique, String nomLocal, String type, String statutConservation, String niveauProtection, String description, String photoUrl, SiteNaturelEntity siteNaturel) {
        this.id = id;
        this.nomCommun = nomCommun;
        this.nomScientifique = nomScientifique;
        this.nomLocal = nomLocal;
        this.type = type;
        this.statutConservation = statutConservation;
        this.niveauProtection = niveauProtection;
        this.description = description;
        this.photoUrl = photoUrl;
        this.siteNaturel = siteNaturel;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String nomCommun;
    private String nomScientifique;
    private String nomLocal;
    private String type;
    private String statutConservation;
    private String niveauProtection;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "site_naturel_id", nullable = false)
    private SiteNaturelEntity siteNaturel;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNomCommun() { return nomCommun; }
    public void setNomCommun(String nomCommun) { this.nomCommun = nomCommun; }

    public String getNomScientifique() { return nomScientifique; }
    public void setNomScientifique(String nomScientifique) { this.nomScientifique = nomScientifique; }

    public String getNomLocal() { return nomLocal; }
    public void setNomLocal(String nomLocal) { this.nomLocal = nomLocal; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatutConservation() { return statutConservation; }
    public void setStatutConservation(String statutConservation) { this.statutConservation = statutConservation; }

    public String getNiveauProtection() { return niveauProtection; }
    public void setNiveauProtection(String niveauProtection) { this.niveauProtection = niveauProtection; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public SiteNaturelEntity getSiteNaturel() { return siteNaturel; }
    public void setSiteNaturel(SiteNaturelEntity siteNaturel) { this.siteNaturel = siteNaturel; }
}

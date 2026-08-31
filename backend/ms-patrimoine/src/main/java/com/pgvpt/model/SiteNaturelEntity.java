package com.pgvpt.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sites_naturels")
@PrimaryKeyJoinColumn(name = "patrimoine_id")
public class SiteNaturelEntity extends PatrimoineEntity {

    public SiteNaturelEntity() {}

    public SiteNaturelEntity(Double superficie, String natureSite, String ecosysteme, String biodiversite, Boolean zoneProtegee, String categorieProtection, List<String> risquesEnvironnementaux, List<String> ressourcesNaturelles, List<String> activitesEcotouristiques, Integer capaciteAccueil, List<EspeceProtegeeEntity> especesProtegees) {
        this.superficie = superficie;
        this.natureSite = natureSite;
        this.ecosysteme = ecosysteme;
        this.biodiversite = biodiversite;
        this.zoneProtegee = zoneProtegee;
        this.categorieProtection = categorieProtection;
        this.risquesEnvironnementaux = risquesEnvironnementaux;
        this.ressourcesNaturelles = ressourcesNaturelles;
        this.activitesEcotouristiques = activitesEcotouristiques;
        this.capaciteAccueil = capaciteAccueil;
        this.especesProtegees = especesProtegees;
    }
    private Double superficie;
    private String natureSite;
    private String ecosysteme;
    @Column(columnDefinition = "TEXT")
    private String biodiversite;
    private Boolean zoneProtegee;
    private String categorieProtection;
    
    @ElementCollection
    @CollectionTable(name = "site_risques_environnementaux", joinColumns = @JoinColumn(name = "site_id"))
    @Column(name = "risque")
    private List<String> risquesEnvironnementaux = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "site_ressources_naturelles", joinColumns = @JoinColumn(name = "site_id"))
    @Column(name = "ressource")
    private List<String> ressourcesNaturelles = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "site_activites_ecotouristiques", joinColumns = @JoinColumn(name = "site_id"))
    @Column(name = "activite")
    private List<String> activitesEcotouristiques = new ArrayList<>();
    
    private Integer capaciteAccueil;
    
    @OneToMany(mappedBy = "siteNaturel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EspeceProtegeeEntity> especesProtegees = new ArrayList<>();

    public Double getSuperficie() { return superficie; }
    public void setSuperficie(Double superficie) { this.superficie = superficie; }

    public String getNatureSite() { return natureSite; }
    public void setNatureSite(String natureSite) { this.natureSite = natureSite; }

    public String getEcosysteme() { return ecosysteme; }
    public void setEcosysteme(String ecosysteme) { this.ecosysteme = ecosysteme; }

    public String getBiodiversite() { return biodiversite; }
    public void setBiodiversite(String biodiversite) { this.biodiversite = biodiversite; }

    public Boolean getZoneProtegee() { return zoneProtegee; }
    public void setZoneProtegee(Boolean zoneProtegee) { this.zoneProtegee = zoneProtegee; }

    public String getCategorieProtection() { return categorieProtection; }
    public void setCategorieProtection(String categorieProtection) { this.categorieProtection = categorieProtection; }

    public List<String> getRisquesEnvironnementaux() { return risquesEnvironnementaux; }
    public void setRisquesEnvironnementaux(List<String> risquesEnvironnementaux) { this.risquesEnvironnementaux = risquesEnvironnementaux; }

    public List<String> getRessourcesNaturelles() { return ressourcesNaturelles; }
    public void setRessourcesNaturelles(List<String> ressourcesNaturelles) { this.ressourcesNaturelles = ressourcesNaturelles; }

    public List<String> getActivitesEcotouristiques() { return activitesEcotouristiques; }
    public void setActivitesEcotouristiques(List<String> activitesEcotouristiques) { this.activitesEcotouristiques = activitesEcotouristiques; }

    public Integer getCapaciteAccueil() { return capaciteAccueil; }
    public void setCapaciteAccueil(Integer capaciteAccueil) { this.capaciteAccueil = capaciteAccueil; }

    public List<EspeceProtegeeEntity> getEspecesProtegees() { return especesProtegees; }
    public void setEspecesProtegees(List<EspeceProtegeeEntity> especesProtegees) { this.especesProtegees = especesProtegees; }
}

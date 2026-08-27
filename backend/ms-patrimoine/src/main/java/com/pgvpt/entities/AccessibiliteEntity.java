package com.pgvpt.entities;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accessibilites")
public class AccessibiliteEntity {

    public AccessibiliteEntity() {}

    public AccessibiliteEntity(UUID id, Boolean accessiblePublic, Boolean accessibilitePMR, Boolean accesFauteuilRoulant, Boolean accesTransportPublic, Boolean parking, Boolean guideDisponible, Boolean guideAudio, List<String> langueVisite, Boolean accesEnfant, String conditionsAcces, List<String> restrictions) {
        this.id = id;
        this.accessiblePublic = accessiblePublic;
        this.accessibilitePMR = accessibilitePMR;
        this.accesFauteuilRoulant = accesFauteuilRoulant;
        this.accesTransportPublic = accesTransportPublic;
        this.parking = parking;
        this.guideDisponible = guideDisponible;
        this.guideAudio = guideAudio;
        this.langueVisite = langueVisite;
        this.accesEnfant = accesEnfant;
        this.conditionsAcces = conditionsAcces;
        this.restrictions = restrictions;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Boolean accessiblePublic;
    private Boolean accessibilitePMR;
    private Boolean accesFauteuilRoulant;
    private Boolean accesTransportPublic;
    private Boolean parking;
    private Boolean guideDisponible;
    private Boolean guideAudio;
    @ElementCollection
    @CollectionTable(name = "accessibilite_langues", joinColumns = @JoinColumn(name = "accessibilite_id"))
    @Column(name = "langue")
    private List<String> langueVisite = new ArrayList<>();
    private Boolean accesEnfant;
    @Column(columnDefinition = "TEXT")
    private String conditionsAcces;
    @ElementCollection
    @CollectionTable(name = "accessibilite_restrictions", joinColumns = @JoinColumn(name = "accessibilite_id"))
    @Column(name = "restriction")
    private List<String> restrictions = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Boolean getAccessiblePublic() { return accessiblePublic; }
    public void setAccessiblePublic(Boolean accessiblePublic) { this.accessiblePublic = accessiblePublic; }

    public Boolean getAccessibilitePMR() { return accessibilitePMR; }
    public void setAccessibilitePMR(Boolean accessibilitePMR) { this.accessibilitePMR = accessibilitePMR; }

    public Boolean getAccesFauteuilRoulant() { return accesFauteuilRoulant; }
    public void setAccesFauteuilRoulant(Boolean accesFauteuilRoulant) { this.accesFauteuilRoulant = accesFauteuilRoulant; }

    public Boolean getAccesTransportPublic() { return accesTransportPublic; }
    public void setAccesTransportPublic(Boolean accesTransportPublic) { this.accesTransportPublic = accesTransportPublic; }

    public Boolean getParking() { return parking; }
    public void setParking(Boolean parking) { this.parking = parking; }

    public Boolean getGuideDisponible() { return guideDisponible; }
    public void setGuideDisponible(Boolean guideDisponible) { this.guideDisponible = guideDisponible; }

    public Boolean getGuideAudio() { return guideAudio; }
    public void setGuideAudio(Boolean guideAudio) { this.guideAudio = guideAudio; }

    public List<String> getLangueVisite() { return langueVisite; }
    public void setLangueVisite(List<String> langueVisite) { this.langueVisite = langueVisite; }

    public Boolean getAccesEnfant() { return accesEnfant; }
    public void setAccesEnfant(Boolean accesEnfant) { this.accesEnfant = accesEnfant; }

    public String getConditionsAcces() { return conditionsAcces; }
    public void setConditionsAcces(String conditionsAcces) { this.conditionsAcces = conditionsAcces; }

    public List<String> getRestrictions() { return restrictions; }
    public void setRestrictions(List<String> restrictions) { this.restrictions = restrictions; }
}

package com.pgvpt.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monuments")
public class MonumentEntity extends PatrimoineEntity {

    public MonumentEntity() {}

    private String styleArchitectural;

    private String natureMonument;

    private Integer anneeConstruction;

    private String identiteArchitecte;

    private String dimensions;

    private String commanditaire;

    @Column(columnDefinition = "TEXT")
    private String contexteHistorique;

    @ElementCollection
    @CollectionTable(
            name = "monument_materiaux_construction",
            joinColumns = @JoinColumn(name = "monument_id")
    )
    @Column(name = "materiau")
    private List<String> materiauxConstruction = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "monument_personnages_associes",
            joinColumns = @JoinColumn(name = "monument_id")
    )
    @Column(name = "personnage")
    private List<String> personnagesAssocies = new ArrayList<>();

    public String getStyleArchitectural() { return styleArchitectural; }
    public void setStyleArchitectural(String styleArchitectural) { this.styleArchitectural = styleArchitectural; }

    public String getNatureMonument() { return natureMonument; }
    public void setNatureMonument(String natureMonument) { this.natureMonument = natureMonument; }

    public Integer getAnneeConstruction() { return anneeConstruction; }
    public void setAnneeConstruction(Integer anneeConstruction) { this.anneeConstruction = anneeConstruction; }

    public String getIdentiteArchitecte() { return identiteArchitecte; }
    public void setIdentiteArchitecte(String identiteArchitecte) { this.identiteArchitecte = identiteArchitecte; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public String getCommanditaire() { return commanditaire; }
    public void setCommanditaire(String commanditaire) { this.commanditaire = commanditaire; }

    public String getContexteHistorique() { return contexteHistorique; }
    public void setContexteHistorique(String contexteHistorique) { this.contexteHistorique = contexteHistorique; }

    public List<String> getMateriauxConstruction() { return materiauxConstruction; }
    public void setMateriauxConstruction(List<String> materiauxConstruction) { this.materiauxConstruction = materiauxConstruction; }

    public List<String> getPersonnagesAssocies() { return personnagesAssocies; }
    public void setPersonnagesAssocies(List<String> personnagesAssocies) { this.personnagesAssocies = personnagesAssocies; }
}


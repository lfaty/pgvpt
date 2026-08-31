package com.pgvpt.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "collections_musee")
public class CollectionMuseeEntity {

    public CollectionMuseeEntity() {}

    public CollectionMuseeEntity(UUID id, String nom, String description, Integer nombreOeuvres, String periode, String origine, List<String> themes, MuseeEntity musee) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.nombreOeuvres = nombreOeuvres;
        this.periode = periode;
        this.origine = origine;
        this.themes = themes;
        this.musee = musee;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nom;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer nombreOeuvres;
    private String periode;
    private String origine;
    
    @ElementCollection
    @CollectionTable(name = "collection_musee_themes", joinColumns = @JoinColumn(name = "collection_id"))
    @Column(name = "theme")
    private List<String> themes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "musee_id", nullable = false)
    private MuseeEntity musee;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getNombreOeuvres() { return nombreOeuvres; }
    public void setNombreOeuvres(Integer nombreOeuvres) { this.nombreOeuvres = nombreOeuvres; }

    public String getPeriode() { return periode; }
    public void setPeriode(String periode) { this.periode = periode; }

    public String getOrigine() { return origine; }
    public void setOrigine(String origine) { this.origine = origine; }

    public List<String> getThemes() { return themes; }
    public void setThemes(List<String> themes) { this.themes = themes; }

    public MuseeEntity getMusee() { return musee; }
    public void setMusee(MuseeEntity musee) { this.musee = musee; }
}

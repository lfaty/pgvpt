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
@Table(name = "musees")
@PrimaryKeyJoinColumn(name = "patrimoine_id")
public class MuseeEntity extends PatrimoineEntity {
    
    public MuseeEntity() {}

    public MuseeEntity(Integer nombreCollections, List<CollectionMuseeEntity> collections, List<ExpositionEntity> expositions) {
        this.nombreCollections = nombreCollections;
        this.collections = collections;
        this.expositions = expositions;
    }
    private Integer nombreCollections;
    
    private Integer nombreOeuvres;
    
    private Integer capaciteAccueil;
    
    @ElementCollection
    @CollectionTable(name = "musee_types_collections", joinColumns = @JoinColumn(name = "musee_id"))
    @Column(name = "type_collection")
    private List<String> typesCollections = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "musee_services", joinColumns = @JoinColumn(name = "musee_id"))
    @Column(name = "service")
    private List<String> servicesMusee = new ArrayList<>();
    
    @Column(columnDefinition = "TEXT")
    private String museographie;
    
    @OneToMany(mappedBy = "musee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollectionMuseeEntity> collections = new ArrayList<>();
    
    @OneToMany(mappedBy = "musee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpositionEntity> expositions = new ArrayList<>();

    public Integer getNombreCollections() { return nombreCollections; }
    public void setNombreCollections(Integer nombreCollections) { this.nombreCollections = nombreCollections; }

    public Integer getNombreOeuvres() { return nombreOeuvres; }
    public void setNombreOeuvres(Integer nombreOeuvres) { this.nombreOeuvres = nombreOeuvres; }

    public Integer getCapaciteAccueil() { return capaciteAccueil; }
    public void setCapaciteAccueil(Integer capaciteAccueil) { this.capaciteAccueil = capaciteAccueil; }

    public List<String> getTypesCollections() { return typesCollections; }
    public void setTypesCollections(List<String> typesCollections) { this.typesCollections = typesCollections; }

    public List<String> getServicesMusee() { return servicesMusee; }
    public void setServicesMusee(List<String> servicesMusee) { this.servicesMusee = servicesMusee; }

    public String getMuseographie() { return museographie; }
    public void setMuseographie(String museographie) { this.museographie = museographie; }

    public List<CollectionMuseeEntity> getCollections() { return collections; }
    public void setCollections(List<CollectionMuseeEntity> collections) { this.collections = collections; }

    public List<ExpositionEntity> getExpositions() { return expositions; }
    public void setExpositions(List<ExpositionEntity> expositions) { this.expositions = expositions; }
}

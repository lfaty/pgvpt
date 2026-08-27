package com.pgvpt.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
    
    @OneToMany(mappedBy = "musee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollectionMuseeEntity> collections = new ArrayList<>();
    
    @OneToMany(mappedBy = "musee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpositionEntity> expositions = new ArrayList<>();

    public Integer getNombreCollections() { return nombreCollections; }
    public void setNombreCollections(Integer nombreCollections) { this.nombreCollections = nombreCollections; }

    public List<CollectionMuseeEntity> getCollections() { return collections; }
    public void setCollections(List<CollectionMuseeEntity> collections) { this.collections = collections; }

    public List<ExpositionEntity> getExpositions() { return expositions; }
    public void setExpositions(List<ExpositionEntity> expositions) { this.expositions = expositions; }
}

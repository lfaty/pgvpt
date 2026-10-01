package com.pgvpt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "musees")
@DiscriminatorValue("MUSEE")
@Getter
@Setter
public class MuseeEntity extends PatrimoineEntity {
    private Integer nombreCollections;
    private Integer nombreOeuvres;
    private Integer capaciteAccueil;
    private String museographie;

    @OneToMany(mappedBy = "musee",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CollectionMuseeEntity> collections= new ArrayList<>();

    @OneToMany(mappedBy = "musee",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpositionEntity> expositions = new ArrayList<>();


}
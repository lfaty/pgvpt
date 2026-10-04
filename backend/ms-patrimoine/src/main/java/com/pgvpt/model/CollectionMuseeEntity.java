package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "collection_musee")
@Getter
@Setter
public class CollectionMuseeEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "musee_id", nullable = false)
    private MuseeEntity musee;

    private String nom;
    private String description;
    private Integer nombreOeuvres;
    private String periode;
    private String origine;

    @ElementCollection
    @CollectionTable(name = "collection_theme", joinColumns = @JoinColumn(name = "collection_id"))
    @Column(name = "theme")
    private List<String> themes ;
}

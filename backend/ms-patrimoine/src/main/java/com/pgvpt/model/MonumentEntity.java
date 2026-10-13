package com.pgvpt.model;

import com.pgvpt.enums.NatureMonumentMetier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "monuments")
@DiscriminatorValue("MONUMENT")
@Getter
@Setter
public class MonumentEntity extends PatrimoineEntity {
    @Column(columnDefinition = "TEXT")
    private String styleArchitectural;
    private Integer anneeConstruction;
    private Integer anneeRenovation;
    @Column(columnDefinition = "TEXT")
    private String architecte;
    @Column(columnDefinition = "TEXT")
    private String identiteArchitecte;

    @Enumerated(EnumType.STRING)
    private NatureMonumentMetier natureMonument;

    @Column(columnDefinition = "TEXT")
    private String dimensions;
    @Column(columnDefinition = "TEXT")
    private String commanditaire;
    @Column(columnDefinition = "TEXT")
    private String contexteHistorique;

    @ElementCollection
    @CollectionTable(name = "monument_materiaux", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "materiau", columnDefinition = "TEXT")
    private List<String> materiauxConstruction;

    @ElementCollection
    @CollectionTable(name = "monument_personnage", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "personnage", columnDefinition = "TEXT")
    private List<String> personnagesAssocies ;


}
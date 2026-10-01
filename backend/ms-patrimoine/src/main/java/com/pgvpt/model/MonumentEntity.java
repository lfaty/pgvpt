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
    private String styleArchitectural;
    private Integer anneeConstruction;
    private Integer anneeRenovation;
    private String architecte;
    private String identiteArchitecte;

    @Enumerated(EnumType.STRING)
    private NatureMonumentMetier natureMonument;

    private String dimensions;
    private String commanditaire;
    private String contexteHistorique;

    @ElementCollection
    @CollectionTable(name = "monument_materiaux", joinColumns = @JoinColumn(name = "monument_id"))
    private List<String> materiauxConstruction;

    @ElementCollection
    @CollectionTable(name = "monument_personnage", joinColumns = @JoinColumn(name = "monument_id"))
    @Column(name = "personnage")
    private List<String> personnagesAssocies ;


}
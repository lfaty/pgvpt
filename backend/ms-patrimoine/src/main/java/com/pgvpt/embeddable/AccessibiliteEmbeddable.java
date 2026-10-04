package com.pgvpt.embeddable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@Getter
@Setter
public class AccessibiliteEmbeddable {

    // Changement de 'boolean' à 'Boolean' pour autoriser les valeurs nulles de la BDD
    private Boolean accessiblePublic = false;

    private Boolean accessibilitePMR = false;

    private Boolean accesFauteuilRoulant = false;

    private Boolean accesTransportPublic = false;

    private Boolean parking = false;

    private Boolean guideDisponible = false;

    private Boolean guideAudio = false;

    @ElementCollection
    @CollectionTable(name = "patrimoine_langue_visite", joinColumns = @JoinColumn(name = "patrimoine_id"))
    @Column(name = "langue")
    private Set<String> langueVisite = new HashSet<>();

    // Correction ici : type Boolean (Objet)
    private Boolean accesEnfant = false;

    @Column(columnDefinition = "TEXT")
    private String conditionsAcces;

    @ElementCollection
    @CollectionTable(name = "patrimoine_restriction_acces", joinColumns = @JoinColumn(name = "patrimoine_id"))
    @Column(name = "restriction")
    private Set<String> restrictions = new HashSet<>();

}


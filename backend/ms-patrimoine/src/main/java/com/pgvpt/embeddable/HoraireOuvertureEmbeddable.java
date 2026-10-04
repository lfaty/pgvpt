package com.pgvpt.embeddable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.JourSemaineMetier;
import com.pgvpt.model.PatrimoineEntity;
import jakarta.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class HoraireOuvertureEmbeddable {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private JourSemaineMetier jour;

    private Boolean ouvert;

    @Column(name = "heure_ouverture")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime heureOuverture;

    @Column(name = "heure_fermeture")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime heureFermeture;

    @Column(name = "pause_debut")
    private LocalTime pauseDebut;

    @Column(name = "pause_fin")
    private LocalTime pauseFin;

    @Column(name = "sur_reservation")
    private boolean surReservation;

    @Column(columnDefinition = "TEXT")
    private String commentaire;
}
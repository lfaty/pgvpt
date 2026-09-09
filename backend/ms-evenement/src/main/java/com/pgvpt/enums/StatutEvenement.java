package com.pgvpt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatutEvenement {
    BROUILLON("Brouillon"),

    PROGRAMME("Programme"),

    OUVERT("Ouvert"),

    COMPLET("Complet"),

    TERMINE("Terminé"),

    ANNULE("Annulé");

    private final String label;
}

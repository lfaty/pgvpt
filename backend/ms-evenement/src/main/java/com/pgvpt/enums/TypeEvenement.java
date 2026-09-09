package com.pgvpt.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum TypeEvenement {
    FESTIVAL("Festival"),
    CONCERT("Concert"),
    EXPOSITION("Exposition"),
    CONFERENCE("Conférence"),
    CEREMONIE("Cérémonie"),
    FETE_CULTURELLE("Fête Culturelle"),
    ARTISANAT("Artisanat"),
    SPORT("Sport"),
    VISITE_GUIDEE("Visite Guidée"),
    AUTRE("Autre");

    private final String label;
}

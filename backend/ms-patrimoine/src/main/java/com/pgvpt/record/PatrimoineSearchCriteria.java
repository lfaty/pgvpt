package com.pgvpt.record;

import com.pgvpt.enums.*;

public record PatrimoineSearchCriteria(
        CategoriePatrimoineMetier categorie,
        TypePatrimoineMetier type,
        StatutPatrimoineMetier statut,
        EtatConservationMetier etatConservation,
        Boolean accessiblePublic,
        Boolean inscritUnesco,
        Boolean classePatrimoine,
        String q
) {
}

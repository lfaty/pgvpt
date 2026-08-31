package com.pgvpt.service;

import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.PatrimoineSummary;
import com.pgvpt.dto.TypePatrimoine;
import java.util.List;

public interface RechercheService {
    List<PatrimoineSummary> rechercherPatrimoines(String q, TypePatrimoine type, CategoriePatrimoine categorie, String region, String departement, String commune);
}

package com.pgvpt.service;

import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineCreate;
import com.pgvpt.dto.PatrimoineUpdate;
import com.pgvpt.dto.PagePatrimoine;
import java.util.UUID;

public interface PatrimoineService {

    Patrimoine create(PatrimoineCreate patrimoineCreate);

    Patrimoine getPatrimoine(UUID id);

    void deletePatrimoine(UUID id);

    Patrimoine updatePatrimoine(UUID id, PatrimoineUpdate patrimoineUpdate);

    PagePatrimoine getPatrimoines(Integer page, Integer size, String sort, 
        com.pgvpt.dto.CategoriePatrimoine categorie, com.pgvpt.dto.TypePatrimoine type, String region, 
        String departement, String commune, com.pgvpt.dto.StatutPatrimoine statut, 
        com.pgvpt.dto.EtatConservation etatConservation, Boolean accessiblePublic, 
        Boolean inscritUnesco, Boolean classePatrimoine, String q);
}

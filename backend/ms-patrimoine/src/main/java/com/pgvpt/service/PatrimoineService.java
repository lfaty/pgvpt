package com.pgvpt.service;

import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineCreate;
import com.pgvpt.dto.PatrimoineUpdate;
import com.pgvpt.dto.PagePatrimoine;
import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.TypePatrimoine;
import com.pgvpt.dto.StatutPatrimoine;
import com.pgvpt.dto.EtatConservation;
import java.util.UUID;

public interface PatrimoineService {

    Patrimoine create(PatrimoineCreate patrimoineCreate);

    Patrimoine getPatrimoine(UUID id);

    void deletePatrimoine(UUID id);

    Patrimoine updatePatrimoine(UUID id, PatrimoineUpdate patrimoineUpdate);

    PagePatrimoine getPatrimoines(Integer page, Integer size, String sort, 
        CategoriePatrimoine categorie, TypePatrimoine type, String region, 
        String departement, String commune, StatutPatrimoine statut, 
        EtatConservation etatConservation, Boolean accessiblePublic, 
        Boolean inscritUnesco, Boolean classePatrimoine, String q);
}

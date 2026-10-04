package com.pgvpt.contenu.service;

import com.pgvpt.contenu.enums.StatutContenuMetier;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.dto.Contenu;
import com.pgvpt.dto.ContenuCreate;
import com.pgvpt.dto.ContenuUpdate;
import com.pgvpt.dto.StatutContenu;

import java.util.List;
import java.util.UUID;

public interface ContenuService {
    List<Contenu> findByFilters(UUID patrimoineId, String langue, StatutContenu statut);

    Contenu getById(UUID id);

    Contenu create(ContenuCreate dto);

    Contenu update(UUID id, ContenuUpdate dto);

    void delete(UUID id);

    Contenu valider(UUID id);
    Contenu publier(UUID id);
    Contenu archiver(UUID id);
}

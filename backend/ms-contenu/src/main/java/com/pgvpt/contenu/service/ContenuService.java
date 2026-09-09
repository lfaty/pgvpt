package com.pgvpt.contenu.service;

import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.dto.StatutContenu;

import java.util.List;
import java.util.UUID;

public interface ContenuService {
    List<ContenuEntity> getAll();
    List<ContenuEntity> findByPatrimoineIdLangueAndStatut(UUID patrimoineId, String langue, StatutContenu statut);
    ContenuEntity getById(UUID id);
    ContenuEntity create(ContenuEntity entity);
    ContenuEntity update(UUID id, ContenuEntity entity);
    void delete(UUID id);
}

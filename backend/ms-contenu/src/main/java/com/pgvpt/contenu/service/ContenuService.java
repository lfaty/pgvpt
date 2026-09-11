package com.pgvpt.contenu.service;

import com.pgvpt.contenu.enums.Statut;
import com.pgvpt.contenu.model.ContenuEntity;

import java.util.List;
import java.util.UUID;

public interface ContenuService {
    List<ContenuEntity> getAll();
    List<ContenuEntity> findByFilters(UUID patrimoineId, String langue, Statut statut);
    ContenuEntity getById(UUID id);
    ContenuEntity create(ContenuEntity entity);
    ContenuEntity update(UUID id, ContenuEntity entity);
    void delete(UUID id);
}

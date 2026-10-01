package com.pgvpt.service;

import com.pgvpt.model.PrestationEntity;

import java.util.List;
import java.util.UUID;

public interface PrestationService {

    List<PrestationEntity> findByOffreId(UUID offreId);

    PrestationEntity getById(UUID id);

    PrestationEntity create(UUID offreId, PrestationEntity request);

    PrestationEntity update(UUID id, PrestationEntity request);

    void delete(UUID id);

    PrestationEntity activer(UUID id);

    PrestationEntity desactiver(UUID id);
}

package com.pgvpt.service;

import com.pgvpt.model.SessionEvenementEntity;

import java.util.List;
import java.util.UUID;

public interface SessionEvenementService {

    List<SessionEvenementEntity> findByEvenement(UUID evenementId);

    SessionEvenementEntity getById(UUID id);

    SessionEvenementEntity create(UUID evenementId, SessionEvenementEntity request);

    SessionEvenementEntity update(UUID id, SessionEvenementEntity request);

    void delete(UUID id);
}

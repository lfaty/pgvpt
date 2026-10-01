package com.pgvpt.controller;

import com.pgvpt.api.SessionsApi;
import com.pgvpt.dto.SessionEvenement;
import com.pgvpt.dto.SessionEvenementCreate;
import com.pgvpt.dto.SessionEvenementUpdate;
import com.pgvpt.mapper.SessionEvenementMapper;
import com.pgvpt.model.SessionEvenementEntity;
import com.pgvpt.service.SessionEvenementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SessionEvenementController implements SessionsApi {

    private final SessionEvenementService sessionService;
    private final SessionEvenementMapper sessionMapper;

    @Override
    public ResponseEntity<List<SessionEvenement>> listSessions(UUID id) {

        List<SessionEvenementEntity> sessions = sessionService.findByEvenement(id);

        List<SessionEvenement> response = sessions.stream()
                .map(sessionMapper::toModel)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SessionEvenement> createSession(UUID id, SessionEvenementCreate sessionEvenementCreate) {

        SessionEvenementEntity entity = sessionMapper.toEntity(sessionEvenementCreate);
        SessionEvenementEntity created = sessionService.create(id, entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(sessionMapper.toModel(created));
    }

    @Override
    public ResponseEntity<SessionEvenement> getSession(UUID id) {

        SessionEvenementEntity entity = sessionService.getById(id);

        return ResponseEntity.ok(sessionMapper.toModel(entity));
    }

    @Override
    public ResponseEntity<SessionEvenement> updateSession(UUID id, SessionEvenementUpdate sessionEvenementUpdate) {

        SessionEvenementEntity updated = sessionService.update(id, sessionEvenementUpdate);

        return ResponseEntity.ok(sessionMapper.toModel(updated));
    }

    @Override
    public ResponseEntity<Void> deleteSession(UUID id) {

        sessionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

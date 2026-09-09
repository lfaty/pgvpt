package com.pgvpt.controller;

import com.pgvpt.api.SessionsApi;
import com.pgvpt.dto.SessionEvenement;
import com.pgvpt.dto.SessionEvenementCreate;
import com.pgvpt.dto.SessionEvenementUpdate;
import com.pgvpt.mapper.SessionEvenementMapper;
import com.pgvpt.service.SessionEvenementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SessionEvenementController implements SessionsApi {


    @Override
    public ResponseEntity<SessionEvenement> createSession(UUID id, SessionEvenementCreate sessionEvenementCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSession(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<SessionEvenement> getSession(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<SessionEvenement>> listSessions(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<SessionEvenement> updateSession(UUID id, SessionEvenementUpdate sessionEvenementUpdate) {
        return null;
    }
}

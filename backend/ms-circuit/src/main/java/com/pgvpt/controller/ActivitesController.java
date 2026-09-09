package com.pgvpt.controller;


import com.pgvpt.api.ActivitesApi;
import com.pgvpt.dto.Activite;
import com.pgvpt.dto.ActiviteCreateRequest;
import com.pgvpt.dto.ActiviteUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivitesController implements ActivitesApi {

    @Override
    public ResponseEntity<Activite> activerActivite(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Activite> createActivite(ActiviteCreateRequest activiteCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteActivite(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Activite> desactiverActivite(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Activite> getActivite(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Activite>> listActivites(Integer page, Integer size, String code, String libelle) {
        return null;
    }

    @Override
    public ResponseEntity<Activite> updateActivite(UUID id, ActiviteUpdateRequest activiteUpdateRequest) {
        return null;
    }
}

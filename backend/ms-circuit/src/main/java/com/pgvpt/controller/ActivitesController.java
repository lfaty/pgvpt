package com.pgvpt.controller;


import com.pgvpt.api.ActivitesApi;
import com.pgvpt.dto.Activite;
import com.pgvpt.dto.ActiviteCreateRequest;
import com.pgvpt.dto.ActiviteUpdateRequest;
import com.pgvpt.mapper.ActiviteMapper;
import com.pgvpt.service.ActiviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ActivitesController implements ActivitesApi {

    private final ActiviteService activiteService;
    private final ActiviteMapper activiteMapper;

    @Override
    public ResponseEntity<List<Activite>> listActivites(Integer page, Integer size, String code, String libelle) {
        return ResponseEntity.ok(
                activiteService.rechercher(page, size, code, libelle)
                        .getContent()
                        .stream()
                        .map(activiteMapper::toApi)
                        .toList()
        );
    }

    @Override
    public ResponseEntity<Activite> createActivite(ActiviteCreateRequest activiteCreateRequest) {
        var entity = activiteMapper.toEntity(activiteCreateRequest);
        var saved = activiteService.creer(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(activiteMapper.toApi(saved));
    }

    @Override
    public ResponseEntity<Activite> getActivite(UUID id) {
        return ResponseEntity.ok(activiteMapper.toApi(activiteService.rechercher(id)));
    }

    @Override
    public ResponseEntity<Activite> updateActivite(UUID id, ActiviteUpdateRequest activiteUpdateRequest) {
        var entity = activiteMapper.toEntity(activiteUpdateRequest);
        var updated = activiteService.modifier(id, entity);
        return ResponseEntity.ok(activiteMapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Void> deleteActivite(UUID id) {
        activiteService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Activite> activerActivite(UUID id) {
        return ResponseEntity.ok(activiteMapper.toApi(activiteService.activer(id)));
    }

    @Override
    public ResponseEntity<Activite> desactiverActivite(UUID id) {
        return ResponseEntity.ok(activiteMapper.toApi(activiteService.desactiver(id)));
    }
}

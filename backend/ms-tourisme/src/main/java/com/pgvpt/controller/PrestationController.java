package com.pgvpt.controller;

import com.pgvpt.api.PrestationsApi;
import com.pgvpt.dto.Prestation;
import com.pgvpt.dto.PrestationCreate;
import com.pgvpt.dto.PrestationUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrestationController implements PrestationsApi {

    private final com.pgvpt.service.PrestationService prestationService;
    private final com.pgvpt.mapper.PrestationMapper mapper;

    @Override
    public ResponseEntity<Prestation> activerPrestation(UUID id) {
        return ResponseEntity.ok(mapper.toApi(prestationService.activer(id)));
    }

    @Override
    public ResponseEntity<Prestation> addPrestation(UUID id, PrestationCreate prestationCreate) {
        return ResponseEntity.status(201).body(mapper.toApi(prestationService.create(id, mapper.toEntity(prestationCreate))));
    }

    @Override
    public ResponseEntity<Void> deletePrestation(UUID id) {
        prestationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Prestation> desactiverPrestation(UUID id) {
        return ResponseEntity.ok(mapper.toApi(prestationService.desactiver(id)));
    }

    @Override
    public ResponseEntity<Prestation> getPrestation(UUID id) {
        return ResponseEntity.ok(mapper.toApi(prestationService.getById(id)));
    }

    @Override
    public ResponseEntity<List<Prestation>> listPrestations(UUID id) {
        return ResponseEntity.ok(prestationService.findByOffreId(id).stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<Prestation> updatePrestation(UUID id, PrestationUpdate prestationUpdate) {
        com.pgvpt.model.PrestationEntity entityToUpdate = prestationService.getById(id);
        mapper.updateEntity(prestationUpdate, entityToUpdate);
        return ResponseEntity.ok(mapper.toApi(prestationService.update(id, entityToUpdate)));
    }
}

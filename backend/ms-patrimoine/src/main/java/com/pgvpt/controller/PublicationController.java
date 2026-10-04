package com.pgvpt.controller;

import com.pgvpt.api.StatutApi;
import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineStatutUpdate;
import com.pgvpt.service.PatrimoineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PublicationController implements StatutApi {

    private final PatrimoineService service;

    @Override
    public ResponseEntity<Patrimoine> depublierPatrimoine(@PathVariable UUID id) {
        return ResponseEntity.ok(service.depublier(id));
    }


    @Override
    public ResponseEntity<Patrimoine> updateStatutPatrimoine(UUID id, PatrimoineStatutUpdate patrimoineStatutUpdate) {
        return ResponseEntity.ok(service.updateStatutPatrimoine(id, patrimoineStatutUpdate));
    }
}

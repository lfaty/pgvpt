package com.pgvpt.contenu.controller;

import com.pgvpt.api.*;
import com.pgvpt.contenu.enums.StatutContenuMetier;
import com.pgvpt.dto.*;
import com.pgvpt.contenu.service.ContenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ContenuController implements ContenusApi{

    private final ContenuService service;

    @Override
    public ResponseEntity<Contenu> archiverContenu(UUID id) {
        return ResponseEntity.ok(service.archiver(id));
    }

    @Override
    public ResponseEntity<Contenu> createContenu(ContenuCreate contenuCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(contenuCreate));
    }

    @Override
    public ResponseEntity<Void> deleteContenu(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Contenu> getContenu(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<List<Contenu>> listContenus(UUID patrimoineId, String langue, StatutContenu statut) {
        return ResponseEntity.ok(service.findByFilters(patrimoineId, langue, statut));
    }

    @Override
    public ResponseEntity<Contenu> publierContenu(UUID id) {
        return ResponseEntity.ok(service.publier(id));
    }


    @Override
    public ResponseEntity<Contenu> updateContenu(UUID id, ContenuUpdate contenuUpdate) {
        return ResponseEntity.ok(service.update(id, contenuUpdate));
    }

    @Override
    public ResponseEntity<Contenu> validerContenu(UUID id) {
        return ResponseEntity.ok(service.valider(id));
    }
}

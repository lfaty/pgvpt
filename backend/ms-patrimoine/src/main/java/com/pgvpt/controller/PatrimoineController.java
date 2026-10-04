package com.pgvpt.controller;

import com.pgvpt.api.PatrimoinesApi;
import com.pgvpt.dto.*;
import com.pgvpt.mapper.PatrimoineMapper;
import com.pgvpt.record.PatrimoineSearchCriteria;
import com.pgvpt.service.PatrimoineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PatrimoineController implements PatrimoinesApi {

    private final PatrimoineService service;
    private final PatrimoineMapper mapper;

    @Override
    public ResponseEntity<Patrimoine> createPatrimoine(@Valid @RequestBody PatrimoineCreate patrimoineCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPatrimoine(patrimoineCreate));
    }

    @Override
    public ResponseEntity<Void> deletePatrimoine(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PagePatrimoine> getAllPatrimoines(Integer page, Integer size) {
        return ResponseEntity.ok(service.getAllPatrimoines(page, size));
    }

    @Override
    public ResponseEntity<Patrimoine> getPatrimoine(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<PagePatrimoine> getPatrimoines(Integer page, Integer size, String sort,
            CategoriePatrimoine categorie, TypePatrimoine type, StatutPatrimoine statut,
            EtatConservation etatConservation, Boolean accessiblePublic, Boolean inscritUnesco,
            Boolean classePatrimoine, String q) {
        PatrimoineSearchCriteria criteria = mapper.toCriteria(categorie, type, statut, etatConservation,
                accessiblePublic, inscritUnesco, classePatrimoine, q);
        return ResponseEntity.ok(service.getPatrimoines(page, size, sort, criteria));
    }

    @Override
    public ResponseEntity<Patrimoine> updatePatrimoine(@PathVariable UUID id,
            @Valid @RequestBody PatrimoineUpdate patrimoineUpdate) {
        return ResponseEntity.ok(service.updatePatrimoine(id, patrimoineUpdate));
    }



    @Override
    public ResponseEntity<Patrimoine> patchPatrimoine(UUID id, @Valid PatrimoinePatch patrimoinePatch) {
        return ResponseEntity.ok(service.patchPatrimoine(id, patrimoinePatch));
    }

}

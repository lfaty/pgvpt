package com.pgvpt.contenu.controller;

import com.pgvpt.api.*;
import com.pgvpt.contenu.mapper.ContenuApiMapper;
import com.pgvpt.dto.*;
import com.pgvpt.contenu.service.ContenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class ContenuController implements ContenusApi{

    private final ContenuService service;
    private final ContenuApiMapper mapper;

    public ContenuController(ContenuService service, ContenuApiMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @Override
    public ResponseEntity<Contenu> createContenu(ContenuCreate contenuCreate) {
        var entity = mapper.toEntity(contenuCreate);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Void> deleteContenu(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Contenu> getContenu(UUID id) {
        return ResponseEntity.ok(mapper.toApi(service.getById(id)));
    }

    @Override
    public ResponseEntity<List<Contenu>> listContenus(UUID patrimoineId, String langue, StatutContenu statut) {
        com.pgvpt.contenu.enums.Statut entityStatut = mapper.toEntityEnum(statut);
        return ResponseEntity.ok(
                mapper.toApi(service.findByFilters(patrimoineId, langue, entityStatut))
        );
    }


    @Override
    public ResponseEntity<Contenu> updateContenu(UUID id, ContenuUpdate contenuUpdate) {
        var updated = service.update(id, mapper.toEntity(contenuUpdate));
        return ResponseEntity.ok(mapper.toApi(updated));
    }
}

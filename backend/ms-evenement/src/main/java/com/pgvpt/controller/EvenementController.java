package com.pgvpt.controller;

import com.pgvpt.api.EvenementsApi;
import com.pgvpt.dto.*;
import com.pgvpt.mapper.EvenementMapper;
import com.pgvpt.mapper.SessionEvenementMapper;
import com.pgvpt.model.EvenementEntity;
import com.pgvpt.model.SessionEvenementEntity;
import com.pgvpt.service.EvenementService;
import com.pgvpt.service.SessionEvenementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EvenementController implements EvenementsApi {

    private final EvenementService evenementService;
    private final SessionEvenementService sessionService;
    private final EvenementMapper evenementMapper;
    private final SessionEvenementMapper sessionMapper;

    @Override
    public ResponseEntity<Evenement> createEvenement(EvenementCreate evenementCreate) {

        EvenementEntity entity = evenementMapper.toEntity(evenementCreate);
        EvenementEntity created = evenementService.create(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(evenementMapper.toApi(created));
    }

    @Override
    public ResponseEntity<Evenement> getEvenement(UUID id) {

        EvenementEntity entity = evenementService.getById(id);

        return ResponseEntity.ok(evenementMapper.toApi(entity));
    }

    @Override
    public ResponseEntity<EvenementPage> searchEvenements(
            Integer page, Integer size,
            UUID zoneTouristiqueId, TypeEvenement type, StatutEvenement statut,
            OffsetDateTime dateDebut, OffsetDateTime dateFin, String search) {

        int pageNum = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;

        Instant instantDebut = (dateDebut != null) ? dateDebut.toInstant() : null;
        Instant instantFin = (dateFin != null) ? dateFin.toInstant() : null;

        // Mapper les enums DTO vers les enums service
        com.pgvpt.enums.TypeEvenement typeEntity = (type != null)
                ? com.pgvpt.enums.TypeEvenement.valueOf(type.name()) : null;
        com.pgvpt.enums.StatutEvenement statutEntity = (statut != null)
                ? com.pgvpt.enums.StatutEvenement.valueOf(statut.name()) : null;

        Page<EvenementEntity> result = evenementService.search(
                zoneTouristiqueId, typeEntity, statutEntity,
                instantDebut, instantFin, search,
                PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending()));

        EvenementPage response = new EvenementPage();
        response.setContent(result.getContent().stream().map(evenementMapper::toApi).toList());
        response.setPage(result.getNumber());
        response.setSize(result.getSize());
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Evenement> updateEvenement(UUID id, EvenementUpdate evenementUpdate) {

        EvenementEntity updated = evenementService.update(id, evenementUpdate);

        return ResponseEntity.ok(evenementMapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Void> deleteEvenement(UUID id) {

        evenementService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Evenement> programmerEvenement(UUID id) {

        EvenementEntity entity = evenementService.programmer(id);

        return ResponseEntity.ok(evenementMapper.toApi(entity));
    }

    @Override
    public ResponseEntity<Evenement> ouvrirEvenement(UUID id) {

        EvenementEntity entity = evenementService.ouvrir(id);

        return ResponseEntity.ok(evenementMapper.toApi(entity));
    }

    @Override
    public ResponseEntity<Evenement> annulerEvenement(UUID id) {

        EvenementEntity entity = evenementService.annuler(id);

        return ResponseEntity.ok(evenementMapper.toApi(entity));
    }

    @Override
    public ResponseEntity<Evenement> terminerEvenement(UUID id) {

        EvenementEntity entity = evenementService.terminer(id);

        return ResponseEntity.ok(evenementMapper.toApi(entity));
    }
}

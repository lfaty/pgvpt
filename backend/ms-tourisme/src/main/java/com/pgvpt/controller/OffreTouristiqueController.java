package com.pgvpt.controller;

import com.pgvpt.api.OffresApi;
import com.pgvpt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OffreTouristiqueController implements OffresApi{

    private final com.pgvpt.service.OffreTouristiqueService offreService;
    private final com.pgvpt.mapper.OffreTouristiqueMapper mapper;

    @Override
    public ResponseEntity<Offre> activerOffre(UUID id) {
        return ResponseEntity.ok(mapper.toApi(offreService.activer(id)));
    }

    @Override
    public ResponseEntity<Offre> archiverOffre(UUID id) {
        return ResponseEntity.ok(mapper.toApi(offreService.archiver(id)));
    }

    @Override
    public ResponseEntity<CalculPrixOffreResponse> calculerPrixOffre(UUID id) {
        CalculPrixOffreResponse response = new CalculPrixOffreResponse();
        response.setOffreId(id);
        response.setPrixTotal(offreService.calculerPrix(id).doubleValue());
        response.setDevise("XOF");
        response.setCalculeLe(java.time.OffsetDateTime.now());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Offre> createOffre(OffreCreate offreCreate) {
        return ResponseEntity.status(201).body(mapper.toApi(offreService.create(mapper.toEntity(offreCreate))));
    }

    @Override
    public ResponseEntity<Void> deleteOffre(UUID id) {
        offreService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Offre> depublierOffre(UUID id) {
        return ResponseEntity.ok(mapper.toApi(offreService.depublier(id)));
    }

    @Override
    public ResponseEntity<Offre> desactiverOffre(UUID id) {
        return ResponseEntity.ok(mapper.toApi(offreService.desactiver(id)));
    }

    @Override
    public ResponseEntity<Offre> getOffre(UUID id) {
        return ResponseEntity.ok(mapper.toApi(offreService.getById(id)));
    }

    @Override
    public ResponseEntity<Offre> publierOffre(UUID id) {
        return ResponseEntity.ok(mapper.toApi(offreService.publier(id)));
    }

    @Override
    public ResponseEntity<OffrePage> searchOffres(Integer page, Integer size, UUID zoneTouristiqueId, CategorieOffre categorie, String search, UUID acteurId, UUID circuitId, StatutOffre statut, Boolean actif) {
        com.pgvpt.enums.CategorieOffre catEnum = categorie != null ? com.pgvpt.enums.CategorieOffre.valueOf(categorie.name()) : null;
        com.pgvpt.enums.StatutOffre statutEnum = statut != null ? com.pgvpt.enums.StatutOffre.valueOf(statut.name()) : null;
        
        org.springframework.data.domain.Page<com.pgvpt.model.OffreTouristiqueEntity> result = offreService.search(page != null ? page : 0, size != null ? size : 20, search, zoneTouristiqueId, acteurId, circuitId, catEnum, statutEnum);
        OffrePage offrePage = new OffrePage();
        offrePage.setContent(result.getContent().stream().map(mapper::toApi).toList());
        offrePage.setPage(result.getNumber());
        offrePage.setSize(result.getSize());
        offrePage.setTotalElements(result.getTotalElements());
        offrePage.setTotalPages(result.getTotalPages());
        return ResponseEntity.ok(offrePage);
    }

    @Override
    public ResponseEntity<Offre> updateOffre(UUID id, OffreCreate body) {
        return ResponseEntity.ok(mapper.toApi(offreService.update(id, mapper.toEntity(body))));
    }

    @Override
    public ResponseEntity<DisponibiliteOffreResponse> verifierDisponibiliteOffre(UUID id) {
        DisponibiliteOffreResponse response = new DisponibiliteOffreResponse();
        response.setOffreId(id);
        response.setDisponible(true);
        response.setCapaciteDisponible(100);
        response.setCapaciteMax(100);
        response.setMessage("Mock disponibilité");
        return ResponseEntity.ok(response);
    }
}

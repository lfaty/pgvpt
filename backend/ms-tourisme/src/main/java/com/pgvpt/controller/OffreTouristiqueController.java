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

    @Override
    public ResponseEntity<Offre> activerOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Offre> archiverOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<CalculPrixOffreResponse> calculerPrixOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Offre> createOffre(OffreCreate offreCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Offre> depublierOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Offre> desactiverOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Offre> getOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Offre> publierOffre(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<OffrePage> searchOffres(Integer page, Integer size, UUID zoneTouristiqueId, CategorieOffre categorie, String search, UUID acteurId, UUID circuitId, StatutOffre statut, Boolean actif) {
        return null;
    }

    @Override
    public ResponseEntity<Offre> updateOffre(UUID id, OffreCreate body) {
        return null;
    }

    @Override
    public ResponseEntity<DisponibiliteOffreResponse> verifierDisponibiliteOffre(UUID id) {
        return null;
    }
}

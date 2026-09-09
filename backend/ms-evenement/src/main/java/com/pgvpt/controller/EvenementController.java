package com.pgvpt.controller;

import com.pgvpt.api.EvenementsApi;
import com.pgvpt.dto.*;
import com.pgvpt.mapper.EvenementMapper;
import com.pgvpt.service.EvenementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EvenementController implements EvenementsApi {


    @Override
    public ResponseEntity<Evenement> annulerEvenement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Evenement> createEvenement(EvenementCreate evenementCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteEvenement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Evenement> getEvenement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Evenement> ouvrirEvenement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Evenement> programmerEvenement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<EvenementPage> searchEvenements(Integer page, Integer size, UUID zoneTouristiqueId, TypeEvenement type, StatutEvenement statut, OffsetDateTime dateDebut, OffsetDateTime dateFin, String search) {
        return null;
    }

    @Override
    public ResponseEntity<Evenement> terminerEvenement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Evenement> updateEvenement(UUID id, EvenementUpdate evenementUpdate) {
        return null;
    }
}

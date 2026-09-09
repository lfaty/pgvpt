package com.pgvpt.controller;

import com.pgvpt.api.PrestationsApi;
import com.pgvpt.dto.Prestation;
import com.pgvpt.dto.PrestationCreate;
import com.pgvpt.dto.PrestationUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public class PrestationController implements PrestationsApi {

    @Override
    public ResponseEntity<Prestation> activerPrestation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Prestation> addPrestation(UUID id, PrestationCreate prestationCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePrestation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Prestation> desactiverPrestation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Prestation> getPrestation(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Prestation>> listPrestations(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Prestation> updatePrestation(UUID id, PrestationUpdate prestationUpdate) {
        return null;
    }
}

package com.pgvpt.controller;


import com.pgvpt.api.CircuitsApi;
import com.pgvpt.dto.*;
import com.pgvpt.service.CircuitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CircuitsController implements CircuitsApi {

    private final CircuitService circuitService;

    @Override
    public ResponseEntity<Circuit> archiverCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<CalculDistanceCircuitResponse> calculerDistanceCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<CalculDureeCircuitResponse> calculerDureeCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<CalculPrixCircuitResponse> calculerPrixCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Circuit> createCircuit(CircuitCreateRequest circuitCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Circuit> depublierCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Circuit> getCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Circuit>> listCircuits(Integer page, Integer size, String search, UUID zoneTouristiqueId) {
        return null;
    }

    @Override
    public ResponseEntity<Circuit> publierCircuit(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Circuit> updateCircuit(UUID id, CircuitUpdateRequest circuitUpdateRequest) {
        return null;
    }
}

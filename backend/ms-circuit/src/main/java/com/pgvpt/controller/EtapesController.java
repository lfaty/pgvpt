package com.pgvpt.controller;


import com.pgvpt.api.EtapesApi;
import com.pgvpt.dto.EtapeCircuit;
import com.pgvpt.dto.EtapeCircuitCreateRequest;
import com.pgvpt.dto.EtapeCircuitUpdateRequest;
import com.pgvpt.dto.ReordonnerEtapesRequest;
import com.pgvpt.mapper.EtapeCircuitMapper;
import com.pgvpt.service.EtapeCircuitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EtapesController implements EtapesApi {

    private final EtapeCircuitService etapeCircuitService;
    private final EtapeCircuitMapper etapeCircuitMapper;

    @Override
    public ResponseEntity<EtapeCircuit> createCircuitStep(UUID circuitId, EtapeCircuitCreateRequest etapeCircuitCreateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCircuitStep(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<EtapeCircuit> getCircuitStep(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<EtapeCircuit>> listCircuitSteps(UUID circuitId) {

        return ResponseEntity.ok(etapeCircuitService.getEtapes(circuitId).stream().map(etapeCircuitMapper::toApi).toList());
    }

    @Override
    public ResponseEntity<List<EtapeCircuit>> reordonnerEtapes(UUID circuitId, ReordonnerEtapesRequest reordonnerEtapesRequest) {
        return null;
    }


    @Override
    public ResponseEntity<EtapeCircuit> updateCircuitStep(UUID id, EtapeCircuitUpdateRequest etapeCircuitUpdateRequest) {
        return null;
    }
}

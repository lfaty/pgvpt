package com.pgvpt.controller;


import com.pgvpt.api.CircuitsApi;
import com.pgvpt.dto.*;
import com.pgvpt.mapper.CircuitMapper;
import com.pgvpt.service.CircuitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CircuitsController implements CircuitsApi {

    private final CircuitService circuitService;
    private final CircuitMapper circuitMapper;

    @Override
    public ResponseEntity<List<Circuit>> listCircuits(Integer page, Integer size, String search, UUID zoneTouristiqueId) {
        return ResponseEntity.ok(
                circuitMapper.toApiList(circuitService.rechercher(page, size, search, zoneTouristiqueId).getContent())
        );
    }

    @Override
    public ResponseEntity<Circuit> createCircuit(CircuitCreateRequest circuitCreateRequest) {
        var entity = circuitMapper.toEntity(circuitCreateRequest);
        var saved = circuitService.creer(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(circuitMapper.toApi(saved));
    }

    @Override
    public ResponseEntity<Circuit> getCircuit(UUID id) {
        return ResponseEntity.ok(circuitMapper.toApi(circuitService.rechercherParId(id)));
    }

    @Override
    public ResponseEntity<Circuit> updateCircuit(UUID id, CircuitUpdateRequest circuitUpdateRequest) {
        var entity = circuitMapper.toEntity(circuitUpdateRequest);
        var updated = circuitService.modifier(id, entity);
        return ResponseEntity.ok(circuitMapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Void> deleteCircuit(UUID id) {
        circuitService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Circuit> publierCircuit(UUID id) {
        return ResponseEntity.ok(circuitMapper.toApi(circuitService.publier(id)));
    }

    @Override
    public ResponseEntity<Circuit> depublierCircuit(UUID id) {
        return ResponseEntity.ok(circuitMapper.toApi(circuitService.depublier(id)));
    }

    @Override
    public ResponseEntity<Circuit> archiverCircuit(UUID id) {
        return ResponseEntity.ok(circuitMapper.toApi(circuitService.archiver(id)));
    }

    @Override
    public ResponseEntity<CalculDureeCircuitResponse> calculerDureeCircuit(UUID id) {
        var vm = circuitService.calculerDuree(id);
        CalculDureeCircuitResponse response = new CalculDureeCircuitResponse();
        response.setCircuitId(vm.getCircuitId());
        response.setDureeTotaleMinutes(vm.getDureeTotaleMinutes());
        response.setCalculeLe(vm.getCalculeLe());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CalculDistanceCircuitResponse> calculerDistanceCircuit(UUID id) {
        var vm = circuitService.calculerDistance(id);
        CalculDistanceCircuitResponse response = new CalculDistanceCircuitResponse();
        response.setCircuitId(vm.getCircuitId());
        response.setDistanceTotaleKm(vm.getDistanceTotaleKm());
        response.setCalculeLe(vm.getCalculeLe());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CalculPrixCircuitResponse> calculerPrixCircuit(UUID id) {
        var vm = circuitService.calculerPrix(id);
        CalculPrixCircuitResponse response = new CalculPrixCircuitResponse();
        response.setCircuitId(vm.getCircuitId());
        response.setPrixTotal(vm.getPrixTotal());
        response.setDevise(vm.getDevise());
        response.setCalculeLe(vm.getCalculeLe());
        return ResponseEntity.ok(response);
    }
}

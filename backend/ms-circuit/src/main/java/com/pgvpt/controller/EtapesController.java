package com.pgvpt.controller;


import com.pgvpt.api.EtapesApi;
import com.pgvpt.dto.EtapeCircuit;
import com.pgvpt.dto.EtapeCircuitCreateRequest;
import com.pgvpt.dto.EtapeCircuitUpdateRequest;
import com.pgvpt.dto.ReordonnerEtapesRequest;
import com.pgvpt.mapper.EtapeCircuitMapper;
import com.pgvpt.service.EtapeCircuitService;
import com.pgvpt.viewModel.OrdreEtapeViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<EtapeCircuit>> listCircuitSteps(UUID circuitId) {
        return ResponseEntity.ok(
                etapeCircuitMapper.toApiList(etapeCircuitService.getEtapes(circuitId))
        );
    }

    @Override
    public ResponseEntity<EtapeCircuit> createCircuitStep(UUID circuitId, EtapeCircuitCreateRequest request) {
        var entity = etapeCircuitMapper.toEntity(request);
        var saved = etapeCircuitService.creer(circuitId, entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(etapeCircuitMapper.toApi(saved));
    }

    @Override
    public ResponseEntity<EtapeCircuit> getCircuitStep(UUID id) {
        return ResponseEntity.ok(etapeCircuitMapper.toApi(etapeCircuitService.rechercher(id)));
    }

    @Override
    public ResponseEntity<EtapeCircuit> updateCircuitStep(UUID id, EtapeCircuitUpdateRequest request) {
        var entity = etapeCircuitMapper.toEntity(request);
        var updated = etapeCircuitService.modifier(id, entity);
        return ResponseEntity.ok(etapeCircuitMapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Void> deleteCircuitStep(UUID id) {
        etapeCircuitService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<EtapeCircuit>> reordonnerEtapes(UUID circuitId, ReordonnerEtapesRequest reordonnerEtapesRequest) {
        List<OrdreEtapeViewModel> ordres = reordonnerEtapesRequest.getEtapes().stream()
                .map(o -> new OrdreEtapeViewModel(o.getEtapeId(), o.getOrdre()))
                .toList();

        var etapes = etapeCircuitService.reordonner(circuitId, ordres);
        return ResponseEntity.ok(etapeCircuitMapper.toApiList(etapes));
    }
}

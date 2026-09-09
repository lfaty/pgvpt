package com.pgvpt.controller;

import com.pgvpt.api.OptimisationApi;
import com.pgvpt.dto.ItineraireOptimise;
import com.pgvpt.dto.OptimisationItineraireRequest;
import com.pgvpt.mapper.ItineraireOptimizationMapper;
import com.pgvpt.service.ItineraireOptimizationService;
import com.pgvpt.viewModel.ItineraireOptimiseViewModel;
import com.pgvpt.viewModel.OptimisationItineraireRequestViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OptimisationController implements OptimisationApi {

    private final ItineraireOptimizationService itineraireOptimizationService;
    private final ItineraireOptimizationMapper itineraireOptimizationMapper;

    @Override
    public ResponseEntity<ItineraireOptimise> optimizeCircuitRoute(
            UUID circuitId,
            OptimisationItineraireRequest optimisationItineraireRequest) {

        OptimisationItineraireRequestViewModel request = itineraireOptimizationMapper.toViewModel(optimisationItineraireRequest);

        ItineraireOptimiseViewModel resultat = itineraireOptimizationService.optimiser(circuitId, request);

        return ResponseEntity.ok(itineraireOptimizationMapper.toModel(resultat));
    }

}

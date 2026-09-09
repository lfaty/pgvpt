package com.pgvpt.service;

import com.pgvpt.viewModel.ItineraireOptimiseViewModel;
import com.pgvpt.viewModel.OptimisationItineraireRequestViewModel;

import java.util.UUID;

public interface ItineraireOptimizationService {

    ItineraireOptimiseViewModel optimiser(UUID circuitId, OptimisationItineraireRequestViewModel request);
}
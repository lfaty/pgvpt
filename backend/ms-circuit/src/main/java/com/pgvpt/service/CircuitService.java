package com.pgvpt.service;

import com.pgvpt.model.CircuitEntity;
import com.pgvpt.viewModel.CalculDistanceCircuitResponseViewModel;
import com.pgvpt.viewModel.CalculDureeCircuitResponseViewModel;
import com.pgvpt.viewModel.CalculPrixCircuitResponseViewModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface CircuitService {

    Page<CircuitEntity> rechercher(int page, int size, String search, UUID zoneTouristiqueId);

    CircuitEntity creer(CircuitEntity request);

    CircuitEntity rechercherParId(UUID id);

    CircuitEntity modifier(UUID id, CircuitEntity request);

    void supprimer(UUID id);

    CircuitEntity publier(UUID id);

    CircuitEntity depublier(UUID id);

    CircuitEntity archiver(UUID id);

    CalculDureeCircuitResponseViewModel calculerDuree(UUID id);

    CalculDistanceCircuitResponseViewModel calculerDistance(UUID id);

    CalculPrixCircuitResponseViewModel calculerPrix(UUID id);

}
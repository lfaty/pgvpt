package com.pgvpt.service;

import com.pgvpt.model.EtapeCircuitEntity;

import java.util.List;
import java.util.UUID;

public interface EtapeCircuitService {

    List<EtapeCircuitEntity> getEtapes(UUID circuitId);

    List<EtapeCircuitEntity> rechercherParCircuit(UUID circuitId);

    EtapeCircuitEntity creer(UUID circuitId, EtapeCircuitEntity request);

    EtapeCircuitEntity rechercher(UUID id);

    EtapeCircuitEntity modifier(UUID id, EtapeCircuitEntity request);

    void supprimer(UUID id);

    List<EtapeCircuitEntity> reordonner(UUID circuitId, EtapeCircuitEntity request);
}
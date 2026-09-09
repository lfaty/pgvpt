package com.pgvpt.service.impl;

import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.EtapeCircuitEntity;
import com.pgvpt.repository.CircuitRepository;
import com.pgvpt.repository.EtapeCircuitRepository;
import com.pgvpt.service.EtapeCircuitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class EtapeCircuitServiceImpl implements EtapeCircuitService {

    private final CircuitRepository circuitRepository;
    private final EtapeCircuitRepository etapeRepository;

    @Override
//    @Transactional(readOnly = true)
    public List<EtapeCircuitEntity> getEtapes(UUID circuitId) {

        circuitRepository.findById(circuitId)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable : " + circuitId));

        return etapeRepository.findByCircuitIdOrderByOrdreAsc(circuitId);
    }


    @Override
    public List<EtapeCircuitEntity> rechercherParCircuit(UUID circuitId) {
        return List.of();
    }

    @Override
    public EtapeCircuitEntity creer(UUID circuitId, EtapeCircuitEntity request) {

        circuitRepository.findById(circuitId)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable : " + circuitId));

        if (etapeRepository.existsByCircuitIdAndOrdre(circuitId, request.getOrdre())) {

            throw new BusinessException("L'ordre " + request.getOrdre() + " existe déjà dans ce circuit");
        }

        return etapeRepository.save(request);
    }

    @Override
    public EtapeCircuitEntity rechercher(UUID id) {
        return null;
    }

    @Override
    public EtapeCircuitEntity modifier(UUID id, EtapeCircuitEntity request) {
        return null;
    }

    @Override
    public void supprimer(UUID id) {

    }

    @Override
    @Transactional
    public List<EtapeCircuitEntity> reordonner(UUID circuitId, EtapeCircuitEntity request) {

        circuitRepository.findById(circuitId)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable : " + circuitId));

        List<EtapeCircuitEntity> etapes = etapeRepository.findByCircuitIdOrderByOrdreAsc(circuitId);

        EtapeCircuitEntity etape = etapes.stream()
                .filter(e -> e.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Étape introuvable : " + request.getId()));

        int ancienOrdre = etape.getOrdre();
        int nouvelOrdre = request.getOrdre();

        if (nouvelOrdre < 1 || nouvelOrdre > etapes.size()) {
            throw new BusinessException("L'ordre doit être compris entre 1 et " + etapes.size() + ".");
        }

        if (ancienOrdre == nouvelOrdre) {
            return etapes;
        }

        if (ancienOrdre < nouvelOrdre) {

            etapes.stream()
                    .filter(e ->
                            e.getOrdre() > ancienOrdre
                                    && e.getOrdre() <= nouvelOrdre)
                    .forEach(e -> e.setOrdre(e.getOrdre() - 1));

        } else {

            etapes.stream()
                    .filter(e ->
                            e.getOrdre() >= nouvelOrdre
                                    && e.getOrdre() < ancienOrdre)
                    .forEach(e -> e.setOrdre(e.getOrdre() + 1));
        }

        etape.setOrdre(nouvelOrdre);

        etapeRepository.saveAll(etapes);

        return etapeRepository.findByCircuitIdOrderByOrdreAsc(circuitId);
    }
}

package com.pgvpt.service.impl;

import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.ActiviteEntity;
import com.pgvpt.model.CircuitEntity;
import com.pgvpt.model.EtapeCircuitEntity;
import com.pgvpt.repository.ActiviteRepository;
import com.pgvpt.repository.CircuitRepository;
import com.pgvpt.repository.EtapeCircuitRepository;
import com.pgvpt.service.EtapeCircuitService;
import com.pgvpt.viewModel.OrdreEtapeViewModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class EtapeCircuitServiceImpl implements EtapeCircuitService {

    private final CircuitRepository circuitRepository;
    private final EtapeCircuitRepository etapeRepository;
    private final ActiviteRepository activiteRepository;

    private EtapeCircuitEntity getEntity(UUID id) {
        return etapeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étape introuvable avec l'identifiant : " + id));
    }

    @Override
    public List<EtapeCircuitEntity> getEtapes(UUID circuitId) {

        circuitRepository.findById(circuitId)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable : " + circuitId));

        return etapeRepository.findByCircuitIdOrderByOrdreAsc(circuitId);
    }

    @Override
    public EtapeCircuitEntity creer(UUID circuitId, EtapeCircuitEntity request) {

        CircuitEntity circuit = circuitRepository.findById(circuitId)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable : " + circuitId));

        if (etapeRepository.existsByCircuitIdAndOrdre(circuitId, request.getOrdre())) {
            throw new BusinessException("L'ordre " + request.getOrdre() + " existe déjà dans ce circuit");
        }

        // Vérifier que l'activité existe
        if (request.getActivite() != null && request.getActivite().getId() != null) {
            ActiviteEntity activite = activiteRepository.findById(request.getActivite().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Activité introuvable : " + request.getActivite().getId()));
            request.setActivite(activite);
        }

        request.setCircuit(circuit);

        return etapeRepository.save(request);
    }

    @Override
    public EtapeCircuitEntity rechercher(UUID id) {
        return getEntity(id);
    }

    @Override
    public EtapeCircuitEntity modifier(UUID id, EtapeCircuitEntity request) {
        EtapeCircuitEntity entity = getEntity(id);

        if (request.getOrdre() != null && !request.getOrdre().equals(entity.getOrdre())) {
            if (etapeRepository.existsByCircuitIdAndOrdreAndIdNot(entity.getCircuit().getId(), request.getOrdre(), id)) {
                throw new BusinessException("L'ordre " + request.getOrdre() + " existe déjà dans ce circuit");
            }
            entity.setOrdre(request.getOrdre());
        }

        if (request.getPatrimoineId() != null) {
            entity.setPatrimoineId(request.getPatrimoineId());
        }
        if (request.getActivite() != null && request.getActivite().getId() != null) {
            ActiviteEntity activite = activiteRepository.findById(request.getActivite().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Activité introuvable : " + request.getActivite().getId()));
            entity.setActivite(activite);
        }
        if (request.getDureeMinutes() != null) {
            entity.setDureeMinutes(request.getDureeMinutes());
        }
        if (request.getDistanceDepuisPrecedenteKm() != null) {
            entity.setDistanceDepuisPrecedenteKm(request.getDistanceDepuisPrecedenteKm());
        }
        if (request.getPrix() != null) {
            entity.setPrix(request.getPrix());
        }
        if (request.getDevise() != null) {
            entity.setDevise(request.getDevise());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }

        return etapeRepository.save(entity);
    }

    @Override
    public void supprimer(UUID id) {
        EtapeCircuitEntity entity = getEntity(id);
        etapeRepository.delete(entity);
    }

    @Override
    @Transactional
    public List<EtapeCircuitEntity> reordonner(UUID circuitId, List<OrdreEtapeViewModel> ordres) {

        circuitRepository.findById(circuitId)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable : " + circuitId));

        List<EtapeCircuitEntity> etapes = etapeRepository.findByCircuitIdOrderByOrdreAsc(circuitId);

        // Indexer les étapes par ID
        Map<UUID, EtapeCircuitEntity> etapeMap = etapes.stream()
                .collect(Collectors.toMap(e -> e.getId(), e -> e));

        // Phase 1 : ordres temporaires pour éviter les conflits de contrainte unique
        int ordreTemporaire = 100000;
        for (EtapeCircuitEntity etape : etapes) {
            etape.setOrdre(ordreTemporaire++);
        }
        etapeRepository.saveAll(etapes);
        etapeRepository.flush();

        // Phase 2 : appliquer les vrais ordres
        for (OrdreEtapeViewModel ordre : ordres) {
            EtapeCircuitEntity etape = etapeMap.get(ordre.getEtapeId());
            if (etape == null) {
                throw new ResourceNotFoundException("Étape introuvable : " + ordre.getEtapeId());
            }
            etape.setOrdre(ordre.getOrdre());
        }

        etapeRepository.saveAll(etapes);

        return etapeRepository.findByCircuitIdOrderByOrdreAsc(circuitId);
    }
}

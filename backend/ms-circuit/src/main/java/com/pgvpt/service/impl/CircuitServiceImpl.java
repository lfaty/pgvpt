package com.pgvpt.service.impl;

import com.pgvpt.enums.StatutCircuit;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.CircuitEntity;
import com.pgvpt.model.EtapeCircuitEntity;
import com.pgvpt.repository.CircuitRepository;
import com.pgvpt.repository.EtapeCircuitRepository;
import com.pgvpt.service.CircuitService;
import com.pgvpt.viewModel.CalculDistanceCircuitResponseViewModel;
import com.pgvpt.viewModel.CalculDureeCircuitResponseViewModel;
import com.pgvpt.viewModel.CalculPrixCircuitResponseViewModel;
import com.pgvpt.client.ZoneTouristiqueClient;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CircuitServiceImpl implements CircuitService {

    private final CircuitRepository circuitRepository;
    private final EtapeCircuitRepository etapeRepository;
    private final ZoneTouristiqueClient zoneTouristiqueClient;


    private CircuitEntity getEntity(UUID id) {
        return circuitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Circuit introuvable avec l'identifiant : " + id));
    }

    @Override
    public Page<CircuitEntity> rechercher(int page, int size, String search, UUID zoneTouristiqueId) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CircuitEntity> result;

        if (search != null && zoneTouristiqueId != null) {
            result = circuitRepository.findByNomContainingIgnoreCaseAndZoneTouristiqueId(search, zoneTouristiqueId, pageable);
        }
        else if (search != null) {
            result = circuitRepository.findByNomContainingIgnoreCase(search, pageable);
        }
        else if (zoneTouristiqueId != null) {
            result = circuitRepository.findByZoneTouristiqueId(zoneTouristiqueId, pageable);
        }
        else {
            result = circuitRepository.findAll(pageable);
        }

        return result;
    }

    @Override
    public CircuitEntity creer(CircuitEntity request) {

        verifierZoneTouristique(request.getZoneTouristiqueId());
        verifierDates(request.getDateDebut(), request.getDateFin());

        if (circuitRepository.existsByNomIgnoreCase(request.getNom())) {
            throw new BusinessException("Un circuit portant ce nom existe déjà");
        }
        request.setStatut(StatutCircuit.BROUILLON);

        return circuitRepository.save(request);
    }

    private void verifierDates(java.time.LocalDate dateDebut, java.time.LocalDate dateFin) {

        if (dateDebut != null && dateFin != null && dateFin.isBefore(dateDebut)) {
            throw new BusinessException("La date de fin doit être supérieure ou égale à la date de début");
        }
    }

    private void verifierZoneTouristique(UUID zoneTouristiqueId) {
        if (zoneTouristiqueId == null) {
            return;
        }
        try {
            zoneTouristiqueClient.getById(zoneTouristiqueId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("La zone touristique spécifiée n'existe pas : " + zoneTouristiqueId);
        } catch (FeignException e) {
            throw new BusinessException("Erreur lors de la vérification de la zone touristique : " + e.getMessage());
        }
    }

    @Override
    public CircuitEntity rechercherParId(UUID id) {
        return getEntity(id);
    }

    @Override
    public CircuitEntity modifier(UUID id, CircuitEntity request) {
        CircuitEntity entity = getEntity(id);

        if (entity.getStatut() == StatutCircuit.ARCHIVE) {
            throw new BusinessException("Un circuit archivé ne peut plus être modifié");
        }

        verifierZoneTouristique(request.getZoneTouristiqueId());
        verifierDates(request.getDateDebut(), request.getDateFin());

        // Appliquer les champs modifiables
        entity.setNom(request.getNom());
        entity.setDescription(request.getDescription());
        entity.setType(request.getType());
        entity.setZoneTouristiqueId(request.getZoneTouristiqueId());
        entity.setNiveauDifficulte(request.getNiveauDifficulte());
        entity.setActif(request.isActif());
        entity.setDateDebut(request.getDateDebut());
        entity.setDateFin(request.getDateFin());

        return circuitRepository.save(entity);
    }

    @Override
    public void supprimer(UUID id) {
        CircuitEntity circuit = getEntity(id);

        if (circuit.getStatut() == StatutCircuit.PUBLIE) {
            throw new BusinessException("Un circuit publié ne peut pas être supprimé");
        }

        etapeRepository.deleteByCircuitId(id);

        circuitRepository.delete(circuit);
    }

    @Override
    public CircuitEntity publier(UUID id) {
        CircuitEntity circuit = getEntity(id);

        if (circuit.getStatut() == StatutCircuit.ARCHIVE) {
            throw new BusinessException("Un circuit archivé ne peut pas être publié");
        }

        List<EtapeCircuitEntity> etapes = etapeRepository.findByCircuitIdOrderByOrdreAsc(id);

        if (etapes.isEmpty()) {
            throw new BusinessException("Un circuit doit contenir au moins une étape");
        }

        circuit.setStatut(StatutCircuit.PUBLIE);

        return circuitRepository.save(circuit);
    }

    @Override
    public CircuitEntity depublier(UUID id) {
        CircuitEntity circuit = getEntity(id);

        if (circuit.getStatut() != StatutCircuit.PUBLIE) {
            throw new BusinessException("Seul un circuit publié peut être dépublié");
        }

        circuit.setStatut(StatutCircuit.DEPUBLIE);

        return circuitRepository.save(circuit);
    }

    @Override
    public CircuitEntity archiver(UUID id) {
        CircuitEntity circuit = getEntity(id);

        if (circuit.getStatut() == StatutCircuit.PUBLIE) {
            throw new BusinessException("Un circuit publié doit d'abord être dépublié");
        }

        circuit.setStatut(StatutCircuit.ARCHIVE);

        return circuitRepository.save(circuit);
    }

    @Override
    public CalculDureeCircuitResponseViewModel calculerDuree(UUID id) {

        getEntity(id);

        List<EtapeCircuitEntity> etapes = etapeRepository.findByCircuitIdOrderByOrdreAsc(id);

        int total = etapes.stream()
                .map(EtapeCircuitEntity::getDureeMinutes)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        CalculDureeCircuitResponseViewModel response = new CalculDureeCircuitResponseViewModel();

        response.setCircuitId(id);
        response.setDureeTotaleMinutes(total);
        response.setCalculeLe(OffsetDateTime.now());

        return response;
    }

    @Override
    public CalculDistanceCircuitResponseViewModel calculerDistance(UUID id) {
        getEntity(id);

        List<EtapeCircuitEntity> etapes = etapeRepository.findByCircuitIdOrderByOrdreAsc(id);

        double distance = etapes.stream()
                .map(EtapeCircuitEntity::getDistanceDepuisPrecedenteKm)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        CalculDistanceCircuitResponseViewModel response = new CalculDistanceCircuitResponseViewModel();

        response.setCircuitId(id);
        response.setDistanceTotaleKm(distance);
        response.setCalculeLe(OffsetDateTime.now());

        return response;
    }

    @Override
    public CalculPrixCircuitResponseViewModel calculerPrix(UUID id) {
        getEntity(id);

        List<EtapeCircuitEntity> etapes = etapeRepository.findByCircuitIdOrderByOrdreAsc(id);

        double total = etapes.stream()
                .filter(EtapeCircuitEntity::isActif)
                .map(EtapeCircuitEntity::getPrix)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        CalculPrixCircuitResponseViewModel response = new CalculPrixCircuitResponseViewModel();

        response.setCircuitId(id);
        response.setPrixTotal(total);
        response.setDevise("XOF");
        response.setCalculeLe(OffsetDateTime.now());

        return response;
    }
}

package com.pgvpt.service.impl;

import com.pgvpt.enums.StatutEvenement;
import com.pgvpt.enums.TypeEvenement;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.EvenementEntity;
import com.pgvpt.model.SessionEvenementEntity;
import com.pgvpt.repository.EvenementRepository;
import com.pgvpt.repository.SessionEvenementRepository;
import com.pgvpt.service.EvenementService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EvenementServiceImpl implements EvenementService {

    private final EvenementRepository evenementRepository;
    private final SessionEvenementRepository sessionRepository;


    @Override
    public EvenementEntity create(EvenementEntity request) {

        if (evenementRepository.existsByTitreIgnoreCase(request.getTitre())) {
            throw new BusinessException("Un événement portant ce titre existe déjà.");
        }

        request.setStatut(StatutEvenement.BROUILLON);

        return evenementRepository.save(request);
    }

    @Override
    @Transactional(readOnly = true)
    public EvenementEntity getById(UUID id) {

        return evenementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Événement introuvable : " + id));
    }

    @Override
    public Page<EvenementEntity> search(UUID zoneTouristiqueId, TypeEvenement type, StatutEvenement statut, Instant dateDebut, Instant dateFin, String search, Pageable pageable) {
        return null;
    }

    @Override
    public EvenementEntity update(UUID id, EvenementEntity request) {

        EvenementEntity entity = getById(id);

        if (entity.getStatut() == StatutEvenement.ANNULE) {
            throw new BusinessException("Un événement annulé ne peut plus être modifié.");
        }

        if (entity.getStatut() == StatutEvenement.TERMINE) {
            throw new BusinessException("Un événement terminé ne peut plus être modifié.");
        }

        entity.setCapaciteMax(request.getCapaciteMax());
        entity.setDescription(request.getDescription());
        entity.setStatut(request.getStatut());
        entity.setLangues(request.getLangues());
        entity.setTitre(request.getTitre());
        entity.setDevise(request.getDevise());
        entity.setType(request.getType());
        entity.setTarif(request.getTarif());
        entity.setPatrimoineId(request.getPatrimoineId());
        entity.setReservationRequise(request.isReservationRequise());
        entity.setOrganisateurActeurId(request.getOrganisateurActeurId());
        entity.setZoneTouristiqueId(request.getZoneTouristiqueId());
        entity.setStatut(request.getStatut());

        return evenementRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public EvenementEntity programmer(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() != StatutEvenement.BROUILLON) {
            throw new BusinessException("Seul un événement en brouillon peut être programmé.");
        }

        verifierSessions(id);

        evenement.setStatut(StatutEvenement.PROGRAMME);

        return evenementRepository.save(evenement);
    }

    @Override
    public EvenementEntity ouvrir(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() != StatutEvenement.PROGRAMME) {
            throw new BusinessException("Seul un événement programmé peut être ouvert.");
        }

        verifierSessions(id);

        evenement.setStatut(StatutEvenement.OUVERT);

        return evenementRepository.save(evenement);
    }

    @Override
    public EvenementEntity annuler(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() == StatutEvenement.TERMINE) {
            throw new BusinessException("Un événement terminé ne peut pas être annulé.");
        }

        evenement.setStatut(StatutEvenement.ANNULE);

        return evenementRepository.save(evenement);
    }

    @Override
    public EvenementEntity terminer(UUID id) {

        EvenementEntity evenement = getById(id);

        if (evenement.getStatut() != StatutEvenement.OUVERT) {
            throw new BusinessException("Seul un événement ouvert peut être terminé.");
        }

        evenement.setStatut(StatutEvenement.TERMINE);

        return evenementRepository.save(evenement);
    }

    private void verifierSessions(UUID evenementId) {

        List<SessionEvenementEntity> sessions = sessionRepository.findByEvenementIdOrderByDateDebutAsc(evenementId);

        if (sessions.isEmpty()) {
            throw new BusinessException("L'événement doit posséder au moins une session.");
        }

        sessions.forEach(session -> {

            if (!session.getDateFin().isAfter(session.getDateDebut())) {
                throw new BusinessException("La date de fin doit être postérieure à la date de début.");
            }
        });
    }
}

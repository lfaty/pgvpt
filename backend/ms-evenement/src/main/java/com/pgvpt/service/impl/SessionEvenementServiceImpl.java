package com.pgvpt.service.impl;

import com.pgvpt.enums.StatutEvenement;
import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.EvenementEntity;
import com.pgvpt.model.SessionEvenementEntity;
import com.pgvpt.repository.EvenementRepository;
import com.pgvpt.repository.SessionEvenementRepository;
import com.pgvpt.service.SessionEvenementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionEvenementServiceImpl implements SessionEvenementService {

    private final SessionEvenementRepository repository;
    private final EvenementRepository evenementRepository;


    @Override
    @Transactional(readOnly = true)
    public List<SessionEvenementEntity> findByEvenement(UUID evenementId) {

        verifierEvenement(evenementId);

        return repository.findByEvenementIdOrderByDateDebutAsc(evenementId);
    }

    @Override
    @Transactional(readOnly = true)
    public SessionEvenementEntity getById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session introuvable : " + id));
    }

    @Override
    public SessionEvenementEntity create(UUID evenementId, SessionEvenementEntity request) {

        EvenementEntity evenement = getEvenement(evenementId);

        verifierEvenementModifiable(evenement);

        if (!request.getDateFin().isAfter(request.getDateDebut())) {
            throw new BusinessException("La date de fin doit être postérieure à la date de début.");
        }

        boolean chevauchement = repository.existsByEvenementIdAndDateDebutLessThanAndDateFinGreaterThan(
                evenementId, request.getDateFin(), request.getDateDebut());

        if (chevauchement) {
            throw new BusinessException("La session chevauche une autre session de l'événement.");
        }

        request.setEvenementId(evenementId);

        return repository.save(request);
    }

    @Override
    public SessionEvenementEntity update(UUID id, com.pgvpt.dto.SessionEvenementUpdate request) {

        SessionEvenementEntity existing = getById(id);

        EvenementEntity evenement = getEvenement(existing.getEvenementId());

        verifierEvenementModifiable(evenement);

        // Appliquer les champs non-null (mise à jour partielle)
        if (request.getDateDebut() != null) {
            existing.setDateDebut(request.getDateDebut().toInstant());
        }
        if (request.getDateFin() != null) {
            existing.setDateFin(request.getDateFin().toInstant());
        }
        if (request.getCapacite() != null) {
            existing.setCapacite(request.getCapacite());
        }
        if (request.getLieu() != null) {
            existing.setLieu(request.getLieu());
        }
        if (request.getPatrimoineId() != null) {
            existing.setPatrimoineId(request.getPatrimoineId());
        }

        // Valider les dates après le merge
        if (!existing.getDateFin().isAfter(existing.getDateDebut())) {
            throw new BusinessException("La date de fin doit être postérieure à la date de début.");
        }

        // Vérifier le chevauchement en excluant la session courante
        List<SessionEvenementEntity> autresSessions = repository.findByEvenementIdOrderByDateDebutAsc(existing.getEvenementId());
        for (SessionEvenementEntity autre : autresSessions) {
            if (autre.getId().equals(id)) continue;
            if (existing.getDateDebut().isBefore(autre.getDateFin()) && existing.getDateFin().isAfter(autre.getDateDebut())) {
                throw new BusinessException("La session chevauche une autre session de l'événement.");
            }
        }

        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {

        SessionEvenementEntity session = getById(id);

        EvenementEntity evenement = getEvenement(session.getEvenementId());

        verifierEvenementModifiable(evenement);

        repository.delete(session);
    }

    private EvenementEntity getEvenement(UUID id) {

        return evenementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Événement introuvable : " + id));
    }

    private void verifierEvenement(UUID id) {

        if (!evenementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Événement introuvable : " + id);
        }
    }

    private void verifierEvenementModifiable(EvenementEntity evenement) {

        if (evenement.getStatut() == StatutEvenement.ANNULE) {
            throw new BusinessException("Impossible de modifier les sessions d'un événement annulé.");
        }

        if (evenement.getStatut() == StatutEvenement.TERMINE) {
            throw new BusinessException("Impossible de modifier les sessions d'un événement terminé.");
        }
    }
}

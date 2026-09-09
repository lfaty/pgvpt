package com.pgvpt.service.impl;

import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
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
    public SessionEvenementEntity getById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session introuvable : " + id));
    }

    @Override
    public SessionEvenementEntity create(UUID evenementId, SessionEvenementEntity request) {

        verifierEvenement(evenementId);

        if (!request.getDateFin().isAfter(request.getDateDebut())) {

            throw new BusinessException("La date de fin doit être postérieure à la date de début.");
        }

        boolean chevauchement = repository.existsByEvenementIdAndDateDebutLessThanAndDateFinGreaterThan(evenementId, request.getDateFin(), request.getDateDebut());

        if (chevauchement) {
            throw new BusinessException("La session chevauche une autre session de l'événement.");
        }

        return repository.save(request);
    }

    @Override
    public SessionEvenementEntity update(UUID id, SessionEvenementEntity request) {

        getById(id);

        verifierEvenement(request.getEvenementId());

        if (!request.getDateFin().isAfter(request.getDateDebut())) {
            throw new BusinessException("La date de fin doit être postérieure à la date de début.");
        }

        boolean chevauchement = repository.existsByEvenementIdAndDateDebutLessThanAndDateFinGreaterThan(request.getEvenementId(), request.getDateFin(), request.getDateDebut());

        if (chevauchement) {
            throw new BusinessException("La session chevauche une autre session de l'événement.");
        }

        SessionEvenementEntity entity = new SessionEvenementEntity();
        entity.setEvenementId(request.getEvenementId());
        entity.setLieu(request.getLieu());
        entity.setCapacite(request.getCapacite());
        entity.setDateFin(request.getDateFin());
        entity.setDateDebut(request.getDateDebut());
        entity.setPatrimoineId(request.getPatrimoineId());

        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {

    }

    private void verifierEvenement(UUID id) {

        if (!evenementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Événement introuvable : " + id);
        }
    }

}

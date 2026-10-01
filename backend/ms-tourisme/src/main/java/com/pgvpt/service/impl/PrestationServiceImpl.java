package com.pgvpt.service.impl;

import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.PrestationEntity;
import com.pgvpt.repository.OffreTouristiqueRepository;
import com.pgvpt.repository.PrestationRepository;
import com.pgvpt.service.PrestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PrestationServiceImpl implements PrestationService {

    private final OffreTouristiqueRepository offreTouristiqueRepository;
    private final PrestationRepository prestationRepository;

    @Override
    public List<PrestationEntity> findByOffreId(UUID offreId) {
        return prestationRepository.findByOffreIdOrderByCreatedAtAsc(offreId);
    }

    @Override
    public PrestationEntity getById(UUID id) {
        return prestationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestation introuvable : " + id));
    }

    @Override
    public PrestationEntity create(UUID offreId, PrestationEntity request) {

        offreTouristiqueRepository.findById(offreId)
                .orElseThrow(() -> new ResourceNotFoundException("Offre introuvable : " + offreId));

        if (prestationRepository.existsByOffreIdAndNomIgnoreCase(offreId, request.getNom())) {
            throw new BusinessException("Cette prestation existe déjà pour cette offre.");
        }
        request.setOffreId(offreId);
        request.setActif(true);

        return prestationRepository.save(request);
    }

    @Override
    public PrestationEntity update(UUID id, PrestationEntity request) {
        PrestationEntity existing = getById(id);
        existing.setNom(request.getNom());
        existing.setType(request.getType());
        existing.setDescription(request.getDescription());
        existing.setPrix(request.getPrix());
        existing.setDevise(request.getDevise());
        existing.setActif(request.isActif());
        return prestationRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!prestationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prestation introuvable : " + id);
        }
        prestationRepository.deleteById(id);
    }

    @Override
    public PrestationEntity activer(UUID id) {
        PrestationEntity existing = getById(id);
        existing.setActif(true);
        return prestationRepository.save(existing);
    }

    @Override
    public PrestationEntity desactiver(UUID id) {
        PrestationEntity existing = getById(id);
        existing.setActif(false);
        return prestationRepository.save(existing);
    }
}

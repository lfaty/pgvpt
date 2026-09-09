package com.pgvpt.service.impl;

import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.ActiviteEntity;
import com.pgvpt.model.CircuitEntity;
import com.pgvpt.repository.ActiviteRepository;
import com.pgvpt.service.ActiviteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ActiviteServiceImpl implements ActiviteService {

    private ActiviteRepository activiteRepository;

    @Override
    public Page<ActiviteEntity> rechercher(int page, int size, String code, String libelle) {
        return null;
    }

    @Override
    public ActiviteEntity creer(ActiviteEntity request) {
        return null;
    }

    @Override
    public ActiviteEntity rechercher(UUID id) {
        return null;
    }

    @Override
    public ActiviteEntity modifier(UUID id, ActiviteEntity request) {
        return null;
    }

    @Override
    public void supprimer(UUID id) {

    }

    @Override
    public ActiviteEntity activer(UUID id) {

        ActiviteEntity entity = getEntity(id);
        entity.setActif(true);

        return activiteRepository.save(entity);
    }

    @Override
    public ActiviteEntity desactiver(UUID id) {

        ActiviteEntity entity = getEntity(id);
        entity.setActif(false);

        return activiteRepository.save(entity);
    }

    private ActiviteEntity getEntity(UUID id) {
        return activiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activité introuvable avec l'identifiant : " + id));
    }
}

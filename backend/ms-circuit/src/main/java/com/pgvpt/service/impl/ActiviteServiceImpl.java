package com.pgvpt.service.impl;

import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.ActiviteEntity;
import com.pgvpt.repository.ActiviteRepository;
import com.pgvpt.service.ActiviteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ActiviteServiceImpl implements ActiviteService {

    private final ActiviteRepository activiteRepository;

    private ActiviteEntity getEntity(UUID id) {
        return activiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activité introuvable avec l'identifiant : " + id));
    }

    @Override
    public Page<ActiviteEntity> rechercher(int page, int size, String code, String libelle) {
        Pageable pageable = PageRequest.of(page, size);

        if (code != null && !code.isBlank()) {
            return activiteRepository.findByCodeContainingIgnoreCase(code, pageable);
        }
        if (libelle != null && !libelle.isBlank()) {
            return activiteRepository.findByLibelleContainingIgnoreCase(libelle, pageable);
        }
        return activiteRepository.findAll(pageable);
    }

    @Override
    public ActiviteEntity creer(ActiviteEntity request) {
        if (activiteRepository.existsByCodeIgnoreCase(request.getCode())) {
            throw new BusinessException("Une activité avec le code '" + request.getCode() + "' existe déjà");
        }
        return activiteRepository.save(request);
    }

    @Override
    public ActiviteEntity rechercher(UUID id) {
        return getEntity(id);
    }

    @Override
    public ActiviteEntity modifier(UUID id, ActiviteEntity request) {
        ActiviteEntity entity = getEntity(id);

        entity.setCode(request.getCode());
        entity.setLibelle(request.getLibelle());
        entity.setDescription(request.getDescription());
        entity.setPrixInitial(request.getPrixInitial());
        entity.setActif(request.isActif());

        return activiteRepository.save(entity);
    }

    @Override
    public void supprimer(UUID id) {
        ActiviteEntity entity = getEntity(id);
        activiteRepository.delete(entity);
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
}

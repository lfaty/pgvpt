package com.pgvpt.contenu.service.impl;

import com.pgvpt.contenu.client.PatrimoineClient;
import com.pgvpt.contenu.enums.Statut;
import com.pgvpt.contenu.exception.BusinessException;
import com.pgvpt.contenu.exception.ResourceNotFoundException;
import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.contenu.repository.ContenuRepository;
import com.pgvpt.contenu.service.ContenuService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContenuServiceImpl implements ContenuService {

    private final ContenuRepository repository;
    private final PatrimoineClient patrimoineClient;

    @Override
    @Transactional(readOnly = true)
    public List<ContenuEntity> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContenuEntity> findByFilters(UUID patrimoineId, String langue, Statut statut) {
        return repository.findByFilters(patrimoineId, langue, statut);
    }

    @Override
    @Transactional(readOnly = true)
    public ContenuEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable : " + id));
    }

    @Override
    public ContenuEntity create(ContenuEntity entity) {
        validerPatrimoine(entity.getPatrimoineId());
        return repository.save(entity);
    }

    @Override
    public ContenuEntity update(UUID id, ContenuEntity entity) {
        ContenuEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contenu introuvable : " + id));
        validerPatrimoine(entity.getPatrimoineId());
        entity.setId(existing.getId());
        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contenu introuvable : " + id);
        }
        repository.deleteById(id);
    }

    private void validerPatrimoine(UUID patrimoineId) {
        if (patrimoineId == null) {
            throw new BusinessException("Le champ patrimoineId est obligatoire.");
        }
        try {
            patrimoineClient.getById(patrimoineId);
        } catch (FeignException.NotFound e) {
            log.warn("Patrimoine introuvable : {}", patrimoineId);
            throw new BusinessException("Patrimoine introuvable : " + patrimoineId
                    + ". Veuillez d'abord créer le patrimoine via ms-patrimoine (port 3001).");
        } catch (FeignException e) {
            log.error("Erreur de communication avec ms-patrimoine pour le patrimoine : {}", patrimoineId, e);
            throw new BusinessException("Erreur de communication avec le service patrimoine. Vérifiez que ms-patrimoine est démarré sur le port 3001.");
        }
    }
}

package com.pgvpt.media.service.impl;

import com.pgvpt.media.client.PatrimoineClient;
import com.pgvpt.media.enums.TypeMedia;
import com.pgvpt.media.exception.BusinessException;
import com.pgvpt.media.exception.ResourceNotFoundException;
import com.pgvpt.media.model.MediaEntity;
import com.pgvpt.media.repository.MediaRepository;
import com.pgvpt.media.service.MediaService;
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
public class MediaServiceImpl implements MediaService {

    private final MediaRepository repository;
    private final PatrimoineClient patrimoineClient;

    @Override
    @Transactional(readOnly = true)
    public List<MediaEntity> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaEntity> findByPatrimoineIdAndType(UUID patrimoineId, TypeMedia typeMedia) {
        if (patrimoineId == null && typeMedia == null) {
            return repository.findAll();
        } else if (patrimoineId == null) {
            return repository.findByType(typeMedia);
        } else if (typeMedia == null) {
            return repository.findByPatrimoineId(patrimoineId);
        } else {
            return repository.findByPatrimoineIdAndType(patrimoineId, typeMedia);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MediaEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Média introuvable : " + id));
    }

    @Override
    public MediaEntity create(MediaEntity entity) {
        validerPatrimoine(entity.getPatrimoineId());
        return repository.save(entity);
    }

    @Override
    public MediaEntity update(UUID id, MediaEntity entity) {
        MediaEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Média introuvable : " + id));
        validerPatrimoine(entity.getPatrimoineId());
        entity.setId(existing.getId());
        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Média introuvable : " + id);
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

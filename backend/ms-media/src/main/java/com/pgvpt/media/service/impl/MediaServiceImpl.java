package com.pgvpt.media.service.impl;

import com.pgvpt.dto.Media;
import com.pgvpt.dto.MediaCreate;
import com.pgvpt.dto.TypeMedia;
import com.pgvpt.media.client.PatrimoineClient;
import com.pgvpt.media.enums.TypeMediaMetier;
import com.pgvpt.media.exception.BusinessException;
import com.pgvpt.media.exception.ResourceNotFoundException;
import com.pgvpt.media.mapper.MediaMapper;
import com.pgvpt.media.model.MediaEntity;
import com.pgvpt.media.repository.MediaRepository;
import com.pgvpt.media.service.MediaService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaServiceImpl implements MediaService {

    private final MediaRepository repository;
    private final PatrimoineClient patrimoineClient;
    private final MediaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Media> findByPatrimoineIdAndType(UUID patrimoineId, TypeMedia type) {
        validerPatrimoine(patrimoineId);

        TypeMediaMetier typeMediaMetier = type != null ? mapper.toTypeMetier(type) : null;
        List<MediaEntity> entities;

        if (typeMediaMetier == null) { entities = repository.findByPatrimoineId(patrimoineId); }
        else { entities = repository.findByPatrimoineIdAndType(patrimoineId, typeMediaMetier); }
        return mapper.toDtoList(entities);
    }

    @Override
    public Media getById(UUID id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Média introuvable avec l'ID : " + id));
    }

    @Override
    @Transactional
    public Media create(MediaCreate dto) {
        validerPatrimoine(dto.getPatrimoineId());

        MediaEntity entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public Media update(UUID id, MediaCreate dto) {
        MediaEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Média introuvable avec l'ID : " + id));

        validerPatrimoine(dto.getPatrimoineId());

        mapper.updateEntity(dto, existing);
        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Média introuvable avec l'ID : " + id);
        }
        repository.deleteById(id);
    }

    private void validerPatrimoine(UUID patrimoineId) {
        if (patrimoineId == null) {
            throw new BusinessException("Le champ patrimoineId est obligatoire.");
        }
        try {
            // Validation de l'existence de la ressource dans le microservice distant ms-patrimoine
            patrimoineClient.getById(patrimoineId);
        } catch (FeignException.NotFound e) {
            log.warn("Patrimoine introuvable dans ms-patrimoine : {}", patrimoineId);
            throw new ResourceNotFoundException("Le patrimoine avec l'ID " + patrimoineId
                    + " est introuvable. Veuillez d'abord créer le patrimoine via ms-patrimoine (port 3001).");
        } catch (FeignException e) {
            log.error("Erreur de communication Feign avec ms-patrimoine pour l'ID : {}", patrimoineId, e);
            throw new BusinessException("Erreur de communication avec le service patrimoine. Vérifiez que ms-patrimoine est démarré sur le port 3001.");
        }
    }
}

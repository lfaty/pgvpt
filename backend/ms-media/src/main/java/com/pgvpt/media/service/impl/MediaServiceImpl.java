package com.pgvpt.media.service.impl;

import com.pgvpt.media.enums.TypeMedia;
import com.pgvpt.media.exception.ResourceNotFoundException;
import com.pgvpt.media.model.MediaEntity;
import com.pgvpt.media.repository.MediaRepository;
import com.pgvpt.media.service.MediaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MediaServiceImpl implements MediaService {
    private final MediaRepository repository;

    public MediaServiceImpl(MediaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly=true)
    public List<MediaEntity> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public List<MediaEntity> findByPatrimoineIdAndType(UUID patrimoineId, TypeMedia typeMedia) {
        return repository.findByPatrimoineIdAndType(patrimoineId, typeMedia);
    }

    @Override
    @Transactional(readOnly=true)
    public MediaEntity getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("media introuvable: " + id));
    }

    @Override
    public MediaEntity create(MediaEntity entity) {
        return repository.save(entity);
    }

    @Override
    public MediaEntity update(UUID id, MediaEntity entity) {
        MediaEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("media introuvable: " + id));
        entity.setId(existing.getId());
        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("media introuvable: " + id);
        repository.deleteById(id);
    }
}

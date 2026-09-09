package com.pgvpt.media.service;

import com.pgvpt.media.enums.TypeMedia;
import com.pgvpt.media.model.MediaEntity;

import java.util.List;
import java.util.UUID;

public interface MediaService {
    List<MediaEntity> getAll();
    List<MediaEntity> findByPatrimoineIdAndType(UUID patrimoineId, TypeMedia typeMedia);
    MediaEntity getById(UUID id);
    MediaEntity create(MediaEntity entity);
    MediaEntity update(UUID id, MediaEntity entity);
    void delete(UUID id);

}

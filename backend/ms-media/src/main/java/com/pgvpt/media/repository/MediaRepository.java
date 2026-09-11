package com.pgvpt.media.repository;

import com.pgvpt.media.enums.TypeMedia;
import com.pgvpt.media.model.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<MediaEntity, UUID> {
    List<MediaEntity> findByPatrimoineIdAndType(UUID patrimoineId, TypeMedia typeMedia);
    List<MediaEntity> findByPatrimoineId(UUID patrimoineId);
    List<MediaEntity> findByType(TypeMedia typeMedia);
}

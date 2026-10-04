package com.pgvpt.media.repository;

import com.pgvpt.media.enums.TypeMediaMetier;
import com.pgvpt.media.model.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<MediaEntity, UUID> {
    // Requête combinée optionnelle par défaut, ou gérée dynamiquement par spécification
    List<MediaEntity> findByPatrimoineIdAndType(UUID patrimoineId, TypeMediaMetier type);

    List<MediaEntity> findByPatrimoineId(UUID patrimoineId);

    List<MediaEntity> findByType(TypeMediaMetier type);
}

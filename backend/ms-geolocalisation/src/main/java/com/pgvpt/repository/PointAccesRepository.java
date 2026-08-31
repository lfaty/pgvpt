package com.pgvpt.repository;

import com.pgvpt.model.PointAccesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PointAccesRepository
        extends JpaRepository<PointAccesEntity, UUID> {

    List<PointAccesEntity> findByPatrimoineId(UUID patrimoineId);
}
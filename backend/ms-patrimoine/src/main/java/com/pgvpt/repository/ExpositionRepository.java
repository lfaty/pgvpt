package com.pgvpt.repository;

import com.pgvpt.model.ExpositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpositionRepository extends JpaRepository<ExpositionEntity, UUID> {
}

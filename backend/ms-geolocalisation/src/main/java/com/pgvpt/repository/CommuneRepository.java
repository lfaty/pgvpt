package com.pgvpt.repository;

import com.pgvpt.model.CommuneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommuneRepository extends JpaRepository<CommuneEntity, UUID> {
    boolean existsByCode(String code);
}

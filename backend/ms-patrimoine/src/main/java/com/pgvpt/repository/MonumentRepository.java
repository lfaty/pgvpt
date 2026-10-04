package com.pgvpt.repository;

import com.pgvpt.model.MonumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MonumentRepository extends JpaRepository<MonumentEntity, UUID> {
}

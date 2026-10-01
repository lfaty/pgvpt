package com.pgvpt.repository;

import com.pgvpt.model.MuseeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MuseeRepository extends JpaRepository<MuseeEntity, UUID> {
}

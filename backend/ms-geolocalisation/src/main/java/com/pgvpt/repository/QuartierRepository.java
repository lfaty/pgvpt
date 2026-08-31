package com.pgvpt.repository;

import com.pgvpt.model.PaysEntity;
import com.pgvpt.model.QuartierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuartierRepository extends JpaRepository<QuartierEntity, UUID> {
}

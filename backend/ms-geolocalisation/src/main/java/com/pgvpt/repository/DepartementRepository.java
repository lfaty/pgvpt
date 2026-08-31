package com.pgvpt.repository;

import com.pgvpt.model.CommuneEntity;
import com.pgvpt.model.DepartementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartementRepository extends JpaRepository<DepartementEntity, UUID> {
}

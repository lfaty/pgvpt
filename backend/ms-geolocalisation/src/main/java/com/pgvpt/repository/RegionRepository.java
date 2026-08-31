package com.pgvpt.repository;

import com.pgvpt.model.CommuneEntity;
import com.pgvpt.model.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionRepository extends JpaRepository<RegionEntity, UUID> {
}

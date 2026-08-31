package com.pgvpt.repository;

import com.pgvpt.model.RegionEntity;
import com.pgvpt.model.VillageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VillageRepository extends JpaRepository<VillageEntity, UUID> {
}

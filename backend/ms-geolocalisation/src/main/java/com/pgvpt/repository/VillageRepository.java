package com.pgvpt.repository;

import com.pgvpt.model.RegionEntity;
import com.pgvpt.model.VillageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VillageRepository extends JpaRepository<VillageEntity, UUID> {
    boolean existsByCode(String code);
    List<VillageEntity> findByCommune_Id(UUID communeId);
    Page<VillageEntity> findByCommune_Id(UUID communeId, Pageable pageable);
    boolean existsByCommune_Id(UUID communeId);
}

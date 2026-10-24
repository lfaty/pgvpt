package com.pgvpt.repository;

import com.pgvpt.model.CommuneEntity;
import com.pgvpt.model.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<RegionEntity, UUID> {
    boolean existsByCode(String code);
    Optional<RegionEntity> findByNomIgnoreCase(String nom);
    Optional<RegionEntity> findByCodeIgnoreCase(String code);
    boolean existsByPaysEntity_Id(UUID paysId);
    List<RegionEntity> findByPaysEntity_Id(UUID paysId);
}

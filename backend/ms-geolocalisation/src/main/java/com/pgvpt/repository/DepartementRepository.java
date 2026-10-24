package com.pgvpt.repository;

import com.pgvpt.model.CommuneEntity;
import com.pgvpt.model.DepartementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartementRepository extends JpaRepository<DepartementEntity, UUID> {
    boolean existsByCode(String code);
    List<DepartementEntity> findByRegion_Id(UUID regionId);
    Page<DepartementEntity> findByRegion_Id(UUID regionId, Pageable pageable);
    Optional<DepartementEntity> findByNomIgnoreCase(String nom);
    boolean existsByRegion_Id(UUID regionId);
}

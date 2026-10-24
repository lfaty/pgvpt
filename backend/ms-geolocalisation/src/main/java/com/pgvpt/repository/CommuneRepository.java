package com.pgvpt.repository;

import com.pgvpt.model.CommuneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommuneRepository extends JpaRepository<CommuneEntity, UUID> {
    boolean existsByCode(String code);
    List<CommuneEntity> findByDepartement_Id(UUID departementId);
    Page<CommuneEntity> findByDepartement_Id(UUID departementId, Pageable pageable);
    Optional<CommuneEntity> findByNomIgnoreCase(String nom);
    boolean existsByDepartement_Id(UUID departementId);
}

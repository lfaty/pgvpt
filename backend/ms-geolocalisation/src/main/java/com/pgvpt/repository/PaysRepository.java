package com.pgvpt.repository;

import com.pgvpt.model.CommuneEntity;
import com.pgvpt.model.PaysEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaysRepository extends JpaRepository<PaysEntity, UUID> {
    boolean existsByCode(String code);
    Optional<PaysEntity> findByNomIgnoreCase(String nom);
    Optional<PaysEntity> findByCodeIgnoreCase(String code);
}

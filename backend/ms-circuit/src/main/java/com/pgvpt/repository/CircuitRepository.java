package com.pgvpt.repository;

import com.pgvpt.model.CircuitEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CircuitRepository extends JpaRepository<CircuitEntity, UUID> {

    Page<CircuitEntity> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    Page<CircuitEntity> findByZoneTouristiqueId(UUID zoneTouristiqueId, Pageable pageable);

    Page<CircuitEntity> findByNomContainingIgnoreCaseAndZoneTouristiqueId(String nom, UUID zoneTouristiqueId, Pageable pageable);

    boolean existsByNomIgnoreCase(String nom);
}

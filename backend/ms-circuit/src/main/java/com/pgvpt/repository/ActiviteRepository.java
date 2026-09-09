package com.pgvpt.repository;

import com.pgvpt.model.ActiviteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActiviteRepository
        extends JpaRepository<ActiviteEntity, UUID> {

    Optional<ActiviteEntity> findByCodeIgnoreCase(String code);

    Page<ActiviteEntity> findByCodeContainingIgnoreCase(String code, Pageable pageable);

    Page<ActiviteEntity> findByLibelleContainingIgnoreCase(String libelle, Pageable pageable);

    boolean existsByCodeIgnoreCase(String code);
}

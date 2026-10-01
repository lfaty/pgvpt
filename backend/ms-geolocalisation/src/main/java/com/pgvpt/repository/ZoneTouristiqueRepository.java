package com.pgvpt.repository;

import com.pgvpt.model.ZoneTouristiqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZoneTouristiqueRepository extends JpaRepository<ZoneTouristiqueEntity, UUID> {
    boolean existsByCode(String code);
    boolean existsByNom(String nom);
    boolean existsByCodeAndIdNot(String code, UUID id);
    boolean existsByNomAndIdNot(String nom, UUID id);
}


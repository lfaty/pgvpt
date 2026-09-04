package com.pgvpt.repository;

import com.pgvpt.model.ZoneGeographiqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZoneGeographiqueRepository extends JpaRepository<ZoneGeographiqueEntity, UUID> {
    boolean existsByCode(String code);
    boolean existsByNom(String nom);
    boolean existsByCodeAndIdNot(String code, UUID id);
    boolean existsByNomAndIdNot(String nom, UUID id);
}

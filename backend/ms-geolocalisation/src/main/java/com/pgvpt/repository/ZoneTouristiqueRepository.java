package com.pgvpt.repository;

import com.pgvpt.model.ZoneGeographiqueEntity;
import com.pgvpt.model.ZoneTouristiqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ZoneTouristiqueRepository extends JpaRepository<ZoneTouristiqueEntity, UUID> {

    List<ZoneGeographiqueEntity> findByDepartementIgnoreCase(String departement);

    List<ZoneGeographiqueEntity> findByCommuneIgnoreCase(String commune);
}

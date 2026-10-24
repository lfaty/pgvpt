package com.pgvpt.repository;

import com.pgvpt.model.ZoneTouristiqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZoneTouristiqueRepository extends JpaRepository<ZoneTouristiqueEntity, UUID> {
    boolean existsByCode(String code);
    boolean existsByNom(String nom);
    boolean existsByCodeAndIdNot(String code, UUID id);
    boolean existsByNomAndIdNot(String nom, UUID id);
    boolean existsByVillage_Id(UUID villageId);
    boolean existsByCommune_Id(UUID communeId);
    boolean existsByDepartement_Id(UUID departementId);
    boolean existsByZoneGeographique_Id(UUID zoneGeographiqueId);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("UPDATE ZoneTouristiqueEntity z SET z.village = null WHERE z.village.id = :villageId")
    void detachVillage(@org.springframework.data.repository.query.Param("villageId") UUID villageId);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("UPDATE ZoneTouristiqueEntity z SET z.commune = null WHERE z.commune.id = :communeId")
    void detachCommune(@org.springframework.data.repository.query.Param("communeId") UUID communeId);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("UPDATE ZoneTouristiqueEntity z SET z.departement = null WHERE z.departement.id = :departementId")
    void detachDepartement(@org.springframework.data.repository.query.Param("departementId") UUID departementId);
}


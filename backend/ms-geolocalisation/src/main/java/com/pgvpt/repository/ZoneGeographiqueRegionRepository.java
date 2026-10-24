package com.pgvpt.repository;

import com.pgvpt.model.ZoneGeographiqueRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ZoneGeographiqueRegionRepository extends JpaRepository<ZoneGeographiqueRegionEntity, UUID> {

    @Modifying
    @Query("DELETE FROM ZoneGeographiqueRegionEntity z WHERE z.region.id = :regionId")
    void deleteByRegionId(@Param("regionId") UUID regionId);

    @Modifying
    @Query("DELETE FROM ZoneGeographiqueRegionEntity z WHERE z.zoneGeographique.id = :zoneId")
    void deleteByZoneGeographiqueId(@Param("zoneId") UUID zoneId);
}

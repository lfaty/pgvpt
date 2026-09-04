package com.pgvpt.repository;

import com.pgvpt.model.GeolocalisationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface GeolocalisationRepository extends JpaRepository<GeolocalisationEntity, UUID> {

    Optional<GeolocalisationEntity> findByPatrimoineId(UUID patrimoineId);

    Optional<GeolocalisationEntity> findByZoneTouristiqueId(UUID zoneTouristiqueId);

    boolean existsByPatrimoineId(UUID patrimoineId);

    /**
     * Recherche par emprise géographique (bounding box).
     */
    @Query("SELECT g FROM GeolocalisationEntity g WHERE " +
           "g.latitude BETWEEN :minLat AND :maxLat AND " +
           "g.longitude BETWEEN :minLon AND :maxLon")
    Page<GeolocalisationEntity> findInBoundingBox(
        @Param("minLat") double minLat,
        @Param("minLon") double minLon,
        @Param("maxLat") double maxLat,
        @Param("maxLon") double maxLon,
        Pageable pageable);
}

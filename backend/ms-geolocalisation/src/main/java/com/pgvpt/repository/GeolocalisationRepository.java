package com.pgvpt.repository;


import com.pgvpt.model.GeolocalisationEntity;
import com.pgvpt.model.ZoneTouristiqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeolocalisationRepository extends JpaRepository<GeolocalisationEntity, UUID> {

    Optional<GeolocalisationEntity> findByPatrimoineId(UUID patrimoineId);

    Optional<GeolocalisationEntity> findByZoneTouristiqueId(UUID zoneTouristiqueId);

    boolean existsByPatrimoineId(UUID patrimoineId);

//    List<GeolocalisationEntity> findByRegionIgnoreCase(String region);
//
//    List<GeolocalisationEntity> findByDepartementIgnoreCase(String departement);
//
//    List<GeolocalisationEntity> findByCommuneIgnoreCase(String commune);
//
//    List<GeolocalisationEntity> findByRegionIgnoreCaseAndCommuneIgnoreCase(String region, String commune);
//    List<GeolocalisationEntity> findByRegionIgnoreCaseAndDepartementIgnoreCase(String region, String departement);
//    List<GeolocalisationEntity> findByDepartementIgnoreCaseAndCommuneIgnoreCase(String departement, String commune);

//    @Query("SELECT g FROM GeolocalisationEntity g WHERE " +
//            "(6371 * acos(cos(radians(:lat)) * cos(radians(g.latitude)) * " +
//            "cos(radians(g.longitude) - radians(:lng)) + " +
//            "sin(radians(:lat)) * sin(radians(g.latitude)))) <= :radius " +
//            "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(g.latitude)) * " +
//            "cos(radians(g.longitude) - radians(:lng)) + " +
//            "sin(radians(:lat)) * sin(radians(g.latitude)))) ASC")
//    List<GeolocalisationEntity> findNearby(
//            @Param("lat") double latitude,
//            @Param("lng") double longitude,
//            @Param("radius") double radiusInKm);


}

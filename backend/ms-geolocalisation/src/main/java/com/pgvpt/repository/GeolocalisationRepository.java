package com.pgvpt.repository;

import com.pgvpt.model.GeolocalisationEntity;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GeolocalisationRepository extends JpaRepository<GeolocalisationEntity, UUID> {

    Optional<GeolocalisationEntity> findByPatrimoineId(UUID patrimoineId);

    boolean existsByPatrimoineId(UUID patrimoineId);
    boolean existsByVillage_Id(UUID villageId);
    boolean existsByQuartier_Id(UUID quartierId);
    boolean existsByZoneTouristique_Id(UUID zoneTouristiqueId);

    @org.springframework.data.jpa.repository.Modifying
    @Query("UPDATE GeolocalisationEntity g SET g.village = null WHERE g.village.id = :villageId")
    void detachVillage(@Param("villageId") UUID villageId);

    @org.springframework.data.jpa.repository.Modifying
    @Query("UPDATE GeolocalisationEntity g SET g.quartier = null WHERE g.quartier.id = :quartierId")
    void detachQuartier(@Param("quartierId") UUID quartierId);

    @org.springframework.data.jpa.repository.Modifying
    @Query("UPDATE GeolocalisationEntity g SET g.zoneTouristique = null WHERE g.zoneTouristique.id = :zoneTouristiqueId")
    void detachZoneTouristique(@Param("zoneTouristiqueId") UUID zoneTouristiqueId);

    // HQL/JPQL Hibernate 6 : On retire le "= true" car la fonction agit comme un prédicat booléen autonome
    @Query(value = "SELECT * FROM geolocalisations WHERE st_dwithin(geom, :center, :distanceRad) = true",
            nativeQuery = true)
    Page<GeolocalisationEntity> findWithinRadius(@Param("center") Geometry center, @Param("distanceRad") double distanceRad, Pageable pageable);

    // Bounding Box via ST_Within ou ST_Contains natif (Enveloppe géométrique)
    @Query("SELECT g FROM GeolocalisationEntity g WHERE st_within(g.geom, :bbox)")
    Page<GeolocalisationEntity> findWithinBoundingBox(@Param("bbox") Geometry bbox, Pageable pageable);

    Page<GeolocalisationEntity> findAll(Specification<GeolocalisationEntity> specification, Pageable pageable);
}








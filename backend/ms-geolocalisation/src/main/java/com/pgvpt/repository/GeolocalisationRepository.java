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

    // HQL/JPQL Hibernate 6 : On retire le "= true" car la fonction agit comme un prédicat booléen autonome
    @Query(value = "SELECT * FROM geolocalisations WHERE st_dwithin(geom, :center, :distanceRad) = true",
            nativeQuery = true)
    Page<GeolocalisationEntity> findWithinRadius(@Param("center") Geometry center, @Param("distanceRad") double distanceRad, Pageable pageable);

    // Bounding Box via ST_Within ou ST_Contains natif (Enveloppe géométrique)
    @Query("SELECT g FROM GeolocalisationEntity g WHERE st_within(g.geom, :bbox)")
    Page<GeolocalisationEntity> findWithinBoundingBox(@Param("bbox") Geometry bbox, Pageable pageable);

    Page<GeolocalisationEntity> findAll(Specification<GeolocalisationEntity> specification, Pageable pageable);
}








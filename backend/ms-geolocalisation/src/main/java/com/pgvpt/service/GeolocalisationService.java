package com.pgvpt.service;

import com.pgvpt.model.GeolocalisationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GeolocalisationService {

    GeolocalisationEntity create(GeolocalisationEntity geolocalisationEntity);

    GeolocalisationEntity getById(UUID id);

    GeolocalisationEntity getByPatrimoineId(UUID patrimoineId);

    GeolocalisationEntity getByZoneTouristiqueId(UUID zoneTouristiqueId);

    GeolocalisationEntity update(UUID id, GeolocalisationEntity geolocalisationEntity);

    void delete(UUID id);

    Page<GeolocalisationEntity> getAll(Pageable pageable);

    /**
     * Recherche les géolocalisations dans un rayon autour d'un point central (formule de Haversine).
     */
    Page<GeolocalisationEntity> findNearby(double latitude, double longitude, double rayonKm, Pageable pageable);

    /**
     * Recherche les géolocalisations contenues dans une emprise géographique (bounding box).
     */
    Page<GeolocalisationEntity> findInBoundingBox(double minLat, double minLon, double maxLat, double maxLon, Pageable pageable);

    /**
     * Calcule la distance en km entre deux coordonnées GPS (formule de Haversine).
     */
    double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}

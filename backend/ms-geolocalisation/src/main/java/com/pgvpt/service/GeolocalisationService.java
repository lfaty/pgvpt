package com.pgvpt.service;

import com.pgvpt.dto.*;
import com.pgvpt.record.GeolocalisationSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GeolocalisationService {

    PageGeolocalisation getAll(Pageable pageable);

    Geolocalisation getById(UUID id);

    Geolocalisation getByPatrimoineId(UUID patrimoineId);

    Geolocalisation create(GeolocalisationCreate dto);

    Geolocalisation update(UUID id, GeolocalisationUpdate dto);

    void delete(UUID id);

    Geolocalisation patch(UUID id, GeolocalisationUpdate dto);

    Geolocalisation patchByPatrimoineId(UUID patrimoineId, GeolocalisationUpdate dto);

    Page<Geolocalisation> getWithinRadius(double lat, double lon, double radiusKm, Pageable pageable);

    Distance calculateDistanceBetweenPositions(double lat1, double lon1, double lat2, double lon2);

    Distance calculerDistancePatrimoines(UUID idDepart, UUID idArrivee);

    PagePatrimoineGeographique rechercherDansBoundingBox(Double minLat, Double minLon, Double maxLat, Double maxLon, Pageable pageable);

    PagePatrimoineGeographique searchGeolocalisations(GeolocalisationSearchCriteria criteria, Pageable pageable);

    GeoJsonFeatureCollection exportGeoJson();

    List<FeaturePatrimoine> getPatrimoinesCarte(String region, TypePatrimoine type, CategoriePatrimoine categorie);

    PointAcces addPointAcces(UUID patrimoineId, PointAccesCreate dto);

    List<PointAcces> getPointsAcces(UUID patrimoineId);

    PointAcces updatePointAcces(UUID id, PointAccesCreate dto);

    void deletePointAcces(UUID id);

}

package com.pgvpt.controller;

import com.dev.pgvpt.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.dev.pgvpt.api.GeospatialApi;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GeospatialController implements GeospatialApi{


    @Override
    public ResponseEntity<Distance> calculerDistance(Double latitudeDepart, Double longitudeDepart, Double latitudeArrivee, Double longitudeArrivee) {
        return null;
    }

    @Override
    public ResponseEntity<Distance> calculerDistancePatrimoines(UUID patrimoineIdDepart, UUID patrimoineIdArrivee) {
        return null;
    }

    @Override
    public ResponseEntity<PointAcces> createPointAcces(UUID patrimoineId, PointAccesCreate pointAccesCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePointAcces(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<GeoJsonFeatureCollection> exportGeoJson() {
        return null;
    }

    @Override
    public ResponseEntity<List<FeaturePatrimoine>> getPatrimoinesCarte(String region, String typePatrimoine, String categorie) {
        return null;
    }

    @Override
    public ResponseEntity<List<PointAcces>> getPointsAcces(UUID patrimoineId) {
        return null;
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> rechercherDansBoundingBox(Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude, Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> rechercherDansRayon(Double latitude, Double longitude, Double rayonKm, String typePatrimoine, String categorie, Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> searchGeolocalisations(Double latitude, Double longitude, Double rayonKm, Double minLatitude, Double maxLatitude, Double minLongitude, Double maxLongitude, String localite, String motCle, TypePatrimoine typePatrimoine, String categorie, Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<PointAcces> updatePointAcces(UUID id, PointAccesCreate pointAccesCreate) {
        return null;
    }
}

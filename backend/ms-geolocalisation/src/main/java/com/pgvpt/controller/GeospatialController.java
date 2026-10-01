package com.pgvpt.controller;

import com.pgvpt.api.GeospatialApi;
import com.pgvpt.dto.*;
import com.pgvpt.record.GeolocalisationSearchCriteria;
import com.pgvpt.service.GeolocalisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GeospatialController implements GeospatialApi {

    private final GeolocalisationService service;

    @Override
    public ResponseEntity<Distance> calculerDistance(Double latitudeDepart, Double longitudeDepart, Double latitudeArrivee, Double longitudeArrivee) {
        return ResponseEntity.ok(service.calculateDistanceBetweenPositions(latitudeDepart, longitudeDepart, latitudeArrivee, longitudeArrivee));
    }

    @Override
    public ResponseEntity<Distance> calculerDistancePatrimoines(UUID patrimoineIdDepart, UUID patrimoineIdArrivee) {
        return ResponseEntity.ok(service.calculerDistancePatrimoines(patrimoineIdDepart, patrimoineIdArrivee));
    }

    @Override
    public ResponseEntity<PointAcces> createPointAcces(UUID patrimoineId, PointAccesCreate pointAccesCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addPointAcces(patrimoineId, pointAccesCreate));
    }

    @Override
    public ResponseEntity<Void> deletePointAcces(UUID id) {
        service.deletePointAcces(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<GeoJsonFeatureCollection> exportGeoJson() {
        return ResponseEntity.ok(service.exportGeoJson());
    }

    @Override
    public ResponseEntity<List<FeaturePatrimoine>> getPatrimoinesCarte(String region, TypePatrimoine type, CategoriePatrimoine categorie) {
        return ResponseEntity.ok(service.getPatrimoinesCarte(region, type, categorie));
    }


    @Override
    public ResponseEntity<List<PointAcces>> getPointsAcces(UUID patrimoineId) {
        return ResponseEntity.ok(service.getPointsAcces(patrimoineId));
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> rechercherDansBoundingBox(Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.rechercherDansBoundingBox(minLatitude, minLongitude, maxLatitude, maxLongitude, pageable));
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> rechercherDansRayon(Double latitude, Double longitude, Double rayonKm, TypePatrimoine type, CategoriePatrimoine categorie, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Geolocalisation> domainPage = service.getWithinRadius(latitude, longitude, rayonKm, pageable);

        PagePatrimoineGeographique response = new PagePatrimoineGeographique()
                .page(domainPage.getNumber())
                .size(domainPage.getSize())
                .totalElements(domainPage.getTotalElements())
                .totalPages(domainPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> searchGeolocalisations(Double latitude, Double longitude, Double rayonKm, Double minLatitude, Double maxLatitude, Double minLongitude, Double maxLongitude, String localite, List<String> motCle, TypePatrimoine typePatrimoine, CategoriePatrimoine categorie, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        GeolocalisationSearchCriteria criteria = new GeolocalisationSearchCriteria(latitude, longitude, rayonKm, minLatitude, maxLatitude, minLongitude, maxLongitude, localite, motCle, typePatrimoine, categorie);

        return ResponseEntity.ok(service.searchGeolocalisations(criteria, pageable));
    }

    @Override
    public ResponseEntity<PointAcces> updatePointAcces(UUID id, PointAccesCreate pointAccesCreate) {
        return ResponseEntity.ok(service.updatePointAcces(id, pointAccesCreate));
    }
}

package com.pgvpt.controller;

import com.dev.pgvpt.api.GeospatialApi;
import com.dev.pgvpt.api.model.*;
import com.pgvpt.mapper.GeolocalisationApiMapper;
import com.pgvpt.mapper.PointAccesApiMapper;
import com.pgvpt.model.GeolocalisationEntity;
import com.pgvpt.service.GeolocalisationService;
import com.pgvpt.service.PointAccesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GeospatialController implements GeospatialApi {

    private final GeolocalisationService geolocalisationService;
    private final PointAccesService pointAccesService;
    private final PointAccesApiMapper pointAccesMapper;
    private final GeolocalisationApiMapper geolocalisationMapper;

    // ─────────────────────────── Distance ────────────────────────────

    @Override
    public ResponseEntity<Distance> calculerDistance(Double latitudeDepart, Double longitudeDepart,
                                                      Double latitudeArrivee, Double longitudeArrivee) {
        double distanceKm = geolocalisationService.calculateDistance(
            latitudeDepart, longitudeDepart, latitudeArrivee, longitudeArrivee);

        Distance distance = new Distance(
            distanceKm,
            distanceKm * 1000,
            new Coordonnee(latitudeDepart, longitudeDepart),
            new Coordonnee(latitudeArrivee, longitudeArrivee)
        );

        return ResponseEntity.ok(distance);
    }

    @Override
    public ResponseEntity<Distance> calculerDistancePatrimoines(UUID patrimoineIdDepart, UUID patrimoineIdArrivee) {
        GeolocalisationEntity geoDepart = geolocalisationService.getByPatrimoineId(patrimoineIdDepart);
        GeolocalisationEntity geoArrivee = geolocalisationService.getByPatrimoineId(patrimoineIdArrivee);

        double distanceKm = geolocalisationService.calculateDistance(
            geoDepart.getLatitude(), geoDepart.getLongitude(),
            geoArrivee.getLatitude(), geoArrivee.getLongitude());

        Distance distance = new Distance(
            distanceKm,
            distanceKm * 1000,
            new Coordonnee(geoDepart.getLatitude(), geoDepart.getLongitude()),
            new Coordonnee(geoArrivee.getLatitude(), geoArrivee.getLongitude())
        );

        return ResponseEntity.ok(distance);
    }

    // ─────────────────────────── Points d'accès ────────────────────────────

    @Override
    public ResponseEntity<PointAcces> createPointAcces(UUID patrimoineId, PointAccesCreate pointAccesCreate) {
        var entity = pointAccesMapper.toEntity(pointAccesCreate);
        var created = pointAccesService.createPointAcces(patrimoineId, entity);
        return ResponseEntity.status(201).body(pointAccesMapper.toApi(created));
    }

    @Override
    public ResponseEntity<List<PointAcces>> getPointsAcces(UUID patrimoineId) {
        var entities = pointAccesService.getPointsAccesByPatrimoineId(patrimoineId);
        List<PointAcces> dtos = entities.stream().map(pointAccesMapper::toApi).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PointAcces> updatePointAcces(UUID id, PointAccesCreate pointAccesCreate) {
        var entity = pointAccesMapper.toEntity(pointAccesCreate);
        var updated = pointAccesService.updatePointAcces(id, entity);
        return ResponseEntity.ok(pointAccesMapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Void> deletePointAcces(UUID id) {
        pointAccesService.deletePointAcces(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────── Recherche spatiale ────────────────────────────

    @Override
    public ResponseEntity<PagePatrimoineGeographique> rechercherDansRayon(
            Double latitude, Double longitude, Double rayonKm,
            String typePatrimoine, String categorie, Integer page, Integer size) {

        Page<GeolocalisationEntity> result = geolocalisationService.findNearby(
            latitude, longitude, rayonKm, PageRequest.of(page, size));

        return ResponseEntity.ok(toPagePatrimoineGeographique(result, latitude, longitude));
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> rechercherDansBoundingBox(
            Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude,
            Integer page, Integer size) {

        Page<GeolocalisationEntity> result = geolocalisationService.findInBoundingBox(
            minLatitude, minLongitude, maxLatitude, maxLongitude, PageRequest.of(page, size));

        return ResponseEntity.ok(toPagePatrimoineGeographique(result, null, null));
    }

    @Override
    public ResponseEntity<PagePatrimoineGeographique> searchGeolocalisations(
            Double latitude, Double longitude, Double rayonKm,
            Double minLatitude, Double maxLatitude, Double minLongitude, Double maxLongitude,
            String localite, String motCle, TypePatrimoine typePatrimoine, String categorie,
            Integer page, Integer size) {

        Page<GeolocalisationEntity> result;

        // Si les paramètres de rayon sont fournis, utiliser la recherche par rayon
        if (latitude != null && longitude != null && rayonKm != null) {
            result = geolocalisationService.findNearby(latitude, longitude, rayonKm, PageRequest.of(page, size));
        }
        // Sinon, si les paramètres de bounding box sont fournis, utiliser la recherche par bbox
        else if (minLatitude != null && maxLatitude != null && minLongitude != null && maxLongitude != null) {
            result = geolocalisationService.findInBoundingBox(
                minLatitude, minLongitude, maxLatitude, maxLongitude, PageRequest.of(page, size));
        }
        // Sinon, retourner tout paginé
        else {
            result = geolocalisationService.getAll(PageRequest.of(page, size));
        }

        return ResponseEntity.ok(toPagePatrimoineGeographique(result, latitude, longitude));
    }

    // ─────────────────────────── Cartographie ────────────────────────────

    @Override
    public ResponseEntity<GeoJsonFeatureCollection> exportGeoJson() {
        Page<GeolocalisationEntity> allGeos = geolocalisationService.getAll(PageRequest.of(0, Integer.MAX_VALUE));

        GeoJsonFeatureCollection collection = new GeoJsonFeatureCollection(
            GeoJsonFeatureCollection.TypeEnum.FEATURE_COLLECTION,
            allGeos.getContent().stream().map(this::toGeoJsonFeature).toList()
        );

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<List<FeaturePatrimoine>> getPatrimoinesCarte(String region, String typePatrimoine, String categorie) {
        // Retourne toutes les géolocalisations sous forme de FeaturePatrimoine pour l'affichage sur carte
        Page<GeolocalisationEntity> allGeos = geolocalisationService.getAll(PageRequest.of(0, Integer.MAX_VALUE));

        List<FeaturePatrimoine> features = allGeos.getContent().stream()
            .map(this::toFeaturePatrimoine)
            .toList();

        return ResponseEntity.ok(features);
    }

    // ─────────────────────────── Méthodes utilitaires ────────────────────────────

    private PagePatrimoineGeographique toPagePatrimoineGeographique(
            Page<GeolocalisationEntity> result, Double refLat, Double refLon) {

        List<PatrimoineGeographique> content = result.getContent().stream()
            .map(geo -> {
                PatrimoineGeographique pg = new PatrimoineGeographique();
                pg.setId(geo.getId());
                pg.setLatitude(geo.getLatitude());
                pg.setLongitude(geo.getLongitude());
                pg.setPatrimoineId(geo.getPatrimoineId());
                pg.setEntrepriseId(geo.getEntrepriseId());

                // Calculer la distance si un point de référence est fourni
                if (refLat != null && refLon != null && geo.getLatitude() != null && geo.getLongitude() != null) {
                    pg.setDistanceKm(geolocalisationService.calculateDistance(
                        refLat, refLon, geo.getLatitude(), geo.getLongitude()));
                }

                return pg;
            })
            .toList();

        PagePatrimoineGeographique page = new PagePatrimoineGeographique(
            content,
            result.getNumber(),
            result.getSize(),
            result.getTotalElements(),
            result.getTotalPages(),
            result.isFirst(),
            result.isLast()
        );

        return page;
    }

    private GeoJsonFeature toGeoJsonFeature(GeolocalisationEntity geo) {
        GeoJsonPoint point = new GeoJsonPoint(
            GeoJsonPoint.TypeEnum.POINT,
            List.of(BigDecimal.valueOf(geo.getLongitude()), BigDecimal.valueOf(geo.getLatitude()))
        );

        // Propriétés de la feature
        java.util.Map<String, Object> properties = new java.util.HashMap<>();
        if (geo.getPatrimoineId() != null) properties.put("patrimoineId", geo.getPatrimoineId().toString());
        if (geo.getEntrepriseId() != null) properties.put("entrepriseId", geo.getEntrepriseId().toString());
        if (geo.getAdresse() != null) properties.put("adresse", geo.getAdresse());

        GeoJsonFeature feature = new GeoJsonFeature(
            GeoJsonFeature.TypeEnum.FEATURE,
            point,
            properties
        );

        return feature;
    }

    private FeaturePatrimoine toFeaturePatrimoine(GeolocalisationEntity geo) {
        FeaturePatrimoine feature = new FeaturePatrimoine();
        feature.setId(geo.getId());
        feature.setLatitude(geo.getLatitude());
        feature.setLongitude(geo.getLongitude());
        feature.setPatrimoineId(geo.getPatrimoineId());
        return feature;
    }
}

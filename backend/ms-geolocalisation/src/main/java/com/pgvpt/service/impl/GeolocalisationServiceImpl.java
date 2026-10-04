package com.pgvpt.service.impl;

import com.pgvpt.exception.BusinessException;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.GeolocalisationMapper;
import com.pgvpt.model.GeolocalisationEntity;
import com.pgvpt.model.PointAccesEntity;
import com.pgvpt.record.GeolocalisationSearchCriteria;
import com.pgvpt.repository.*;
import com.pgvpt.dto.*;
import com.pgvpt.service.GeolocalisationService;
import com.pgvpt.client.PatrimoineClient;
import feign.FeignException;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Point; // Import unique et strict pour le géospatial
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeolocalisationServiceImpl implements GeolocalisationService {

    private final GeolocalisationRepository geolocalisationRepository;
    private final PointAccesRepository pointAccesRepository;
    private final GeolocalisationMapper mapper;
    private final PatrimoineClient patrimoineClient;

    private final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);


    @Override
    public PageGeolocalisation getAll(Pageable pageable) {
        Page<GeolocalisationEntity> page = geolocalisationRepository.findAll(pageable);
        return new PageGeolocalisation()
                .content(page.getContent().stream().map(mapper::toDto).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public Geolocalisation getById(UUID id) {
        return geolocalisationRepository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Géolocalisation introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public Geolocalisation getByPatrimoineId(UUID patrimoineId) {
        return geolocalisationRepository.findByPatrimoineId(patrimoineId)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune géolocalisation pour le patrimoine :" + patrimoineId));
    }

    @Override
    @Transactional
    public Geolocalisation create(GeolocalisationCreate dto) {
        validateOneOfConstraint(dto.getPatrimoineId(), dto.getEntrepriseId());

        GeolocalisationEntity entity = mapper.toEntity(dto);
        entity.setGeom(createSafePoint(dto.getLatitude(), dto.getLongitude()));

        return mapper.toDto(geolocalisationRepository.save(entity));
    }

    @Override
    @Transactional
    public Geolocalisation update(UUID id, GeolocalisationUpdate dto) {
        validateOneOfConstraint(dto.getPatrimoineId(), dto.getEntrepriseId());

        GeolocalisationEntity entity = geolocalisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Géolocalisation introuvable"));

        mapper.updateEntity(dto, entity);
        entity.setGeom(createSafePoint(entity.getLatitude(), entity.getLongitude()));

        return mapper.toDto(geolocalisationRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!geolocalisationRepository.existsById(id))
            throw new ResourceNotFoundException("Géolocalisation introuvable");
        geolocalisationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Geolocalisation patch(UUID id, GeolocalisationUpdate dto) {
        return update(id, dto);
    }

    @Override
    @Transactional
    public Geolocalisation patchByPatrimoineId(UUID patrimoineId, GeolocalisationUpdate dto) {
        GeolocalisationEntity entity = geolocalisationRepository.findByPatrimoineId(patrimoineId)
                .orElseThrow(() -> new ResourceNotFoundException("Géolocalisation introuvable pour ce patrimoine"));

        mapper.updateEntity(dto, entity);
        entity.setGeom(createSafePoint(entity.getLatitude(), entity.getLongitude()));

        return mapper.toDto(geolocalisationRepository.save(entity));
    }

    @Override
    public Page<Geolocalisation> getWithinRadius(double lat, double lon, double radiusKm, Pageable pageable) {
        Point center = createSafePoint(lat, lon);
        double distanceInDegrees = radiusKm / 111.32;
        return geolocalisationRepository.findWithinRadius(center, distanceInDegrees, pageable).map(mapper::toDto);
    }

    @Override
    public Distance calculateDistanceBetweenPositions(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceKm = earthRadius * c;

        return new Distance()
                .distanceKm(distanceKm)
                .distanceMetres(distanceKm * 1000)
                .origine(new Coordonnee().latitude(lat1).longitude(lon1))
                .destination(new Coordonnee().latitude(lat2).longitude(lon2));
    }

    @Override
    public Distance calculerDistancePatrimoines(UUID idDepart, UUID idArrivee) {
        GeolocalisationEntity dep = geolocalisationRepository.findByPatrimoineId(idDepart)
                .orElseThrow(() -> new ResourceNotFoundException("Géolocalisation de départ introuvable"));
        GeolocalisationEntity arr = geolocalisationRepository.findByPatrimoineId(idArrivee)
                .orElseThrow(() -> new ResourceNotFoundException("Géolocalisation d'arrivée introuvable"));

        return calculateDistanceBetweenPositions(dep.getLatitude(), dep.getLongitude(), arr.getLatitude(), arr.getLongitude());
    }

    @Override
    public PagePatrimoineGeographique rechercherDansBoundingBox(Double minLat, Double minLon, Double maxLat, Double maxLon, Pageable pageable) {
        Envelope envelope = new Envelope(minLon, maxLon, minLat, maxLat);
        Geometry bbox = gf.toGeometry(envelope);

        Page<GeolocalisationEntity> page = geolocalisationRepository.findWithinBoundingBox(bbox, pageable);

        return new PagePatrimoineGeographique()
                .content(page.getContent().stream().map(e -> new PatrimoineGeographique()
                        .patrimoineId(e.getPatrimoineId()).latitude(e.getLatitude()).longitude(e.getLongitude())
                ).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public PagePatrimoineGeographique searchGeolocalisations(GeolocalisationSearchCriteria criteria, Pageable pageable) {

        Specification<GeolocalisationEntity> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Filtrage spatial par rayon : Exploitation de l'index spatial PostGIS en mètres (Haute Performance)
            if (criteria.latitude() != null && criteria.longitude() != null && criteria.rayonKm() != null) {
                Point center = createSafePoint(criteria.latitude(), criteria.longitude());
                double radiusInMetres = criteria.rayonKm() * 1000.0;

                // Utilisation du cast géographique PostGIS natif pour utiliser un rayon précis en mètres
                predicates.add(cb.isTrue(cb.function("ST_DWithin", Boolean.class,
                        root.get("geom"), cb.literal(center), cb.literal(radiusInMetres))));
            }

            // 2. Filtrage spatial par Bounding Box (Emprise carte)
            if (criteria.minLatitude() != null && criteria.minLongitude() != null &&
                    criteria.maxLatitude() != null && criteria.maxLongitude() != null) {

                Envelope envelope = new Envelope(criteria.minLongitude(), criteria.maxLongitude(), criteria.minLatitude(), criteria.maxLatitude());
                Geometry bbox = gf.toGeometry(envelope);

                predicates.add(cb.isTrue(cb.function("ST_Within", Boolean.class,
                        root.get("geom"), cb.literal(bbox))));
            }

            // 3. Filtrage textuel flou sur la localité globale
            if (criteria.localite() != null && !criteria.localite().isBlank()) {
                String pattern = "%" + criteria.localite().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("adresse")), pattern),
                        cb.like(cb.lower(root.get("lieuDit")), pattern),
                        cb.like(cb.lower(root.get("repere")), pattern)
                ));
            }

            // 4. Filtrage par tableau dynamique de mots-clés (Multi-termes OR)
            if (criteria.motCle() != null && !criteria.motCle().isEmpty()) {
                List<Predicate> keywordPredicates = new ArrayList<>();
                for (String keyword : criteria.motCle()) {
                    if (keyword != null && !keyword.isBlank()) {
                        String pattern = "%" + keyword.toLowerCase() + "%";
                        keywordPredicates.add(cb.or(
                                cb.like(cb.lower(root.get("adresse")), pattern),
                                cb.like(cb.lower(root.get("lieuDit")), pattern),
                                cb.like(cb.lower(root.get("repere")), pattern)
                        ));
                    }
                }
                if (!keywordPredicates.isEmpty()) {
                    predicates.add(cb.or(keywordPredicates.toArray(new Predicate[0])));
                }
            }

            // 5. Filtrages typés (Enums mappés depuis ms-patrimoine)
            if (criteria.typePatrimoine() != null) {
                predicates.add(cb.equal(root.get("type"), mapper.toType(criteria.typePatrimoine())));
            }
            if (criteria.categorie() != null) {
                predicates.add(cb.equal(root.get("categorie"), mapper.toCategorie(criteria.categorie())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // Exécution de la requête paginée optimisée par index
        Page<GeolocalisationEntity> domainPage = geolocalisationRepository.findAll(specification, pageable);

        // Construction fluide de la réponse d'API paginée
        return new PagePatrimoineGeographique()
                .content(domainPage.getContent().stream().map(entity -> new PatrimoineGeographique()
                        .patrimoineId(entity.getPatrimoineId())
                        .entrepriseId(entity.getEntrepriseId())
                        .latitude(entity.getLatitude())
                        .longitude(entity.getLongitude())
                        // CORRECTION : Retourne la vraie valeur textuelle de l'entité lue en base
//                        .categorie(entity.getCategorie() != null ? mapper.toCategorieOpenApi(entity.getCategorie()) : null)
                        .distanceKm(criteria.latitude() != null && criteria.longitude() != null ?
                                calculerDistanceBrute(criteria.latitude(), criteria.longitude(), entity.getLatitude(), entity.getLongitude()) : null)
                ).toList())
                .page(domainPage.getNumber())
                .size(domainPage.getSize())
                .totalElements(domainPage.getTotalElements())
                .totalPages(domainPage.getTotalPages())
                .first(domainPage.isFirst())
                .last(domainPage.isLast());
    }

    @Override
    public GeoJsonFeatureCollection exportGeoJson() {
        List<GeolocalisationEntity> all = geolocalisationRepository.findAll();
        List<GeoJsonFeature> features = new ArrayList<>();

        for (GeolocalisationEntity entity : all) {
            // Sécurisation contre les valeurs nulles pour éviter les plantages
            Double longitude = entity.getLongitude() != null ? entity.getLongitude() : 0.0;
            Double latitude = entity.getLatitude() != null ? entity.getLatitude() : 0.0;

            // Instanciation explicite typée en Double pour satisfaire le DTO OpenAPI
            List<Double> coordinates = new ArrayList<>(2);
            coordinates.add(longitude); // Index 0 : Longitude strict GeoJSON
            coordinates.add(latitude);  // Index 1 : Latitude strict GeoJSON

            GeoJsonPoint point = new GeoJsonPoint()
                    .type(GeoJsonPoint.TypeEnum.POINT)
                    .coordinates(coordinates);

            java.util.Map<String, Object> props = new java.util.HashMap<>();
            props.put("patrimoineId", entity.getPatrimoineId() != null ? entity.getPatrimoineId().toString() : null);
            props.put("adresse", entity.getAdresse());

            features.add(new GeoJsonFeature()
                    .type(GeoJsonFeature.TypeEnum.FEATURE)
                    .geometry(point)
                    .properties(props));
        }

        return new GeoJsonFeatureCollection()
                .type(GeoJsonFeatureCollection.TypeEnum.FEATURE_COLLECTION)
                .features(features);
    }

    @Override
    public List<FeaturePatrimoine> getPatrimoinesCarte(String region, TypePatrimoine type, CategoriePatrimoine categorie) {
        return geolocalisationRepository.findAll().stream()
                .filter(e -> e.getPatrimoineId() != null)
                .map(e -> new FeaturePatrimoine()
                        .patrimoineId(e.getPatrimoineId())
                        .latitude(e.getLatitude())
                        .longitude(e.getLongitude())
                        .couleur("#FF5733"))
                .toList();
    }

    @Override
    @Transactional
    public PointAcces addPointAcces(UUID patrimoineId, PointAccesCreate dto) {
        PointAccesEntity entity = mapper.toEntity(dto);
        entity.setPatrimoineId(patrimoineId);
        return mapper.toDto(pointAccesRepository.save(entity));
    }

    @Override
    public List<PointAcces> getPointsAcces(UUID patrimoineId) {
        return mapper.toPointAccesDtoList(pointAccesRepository.findByPatrimoineId(patrimoineId));
    }

    @Override
    @Transactional
    public PointAcces updatePointAcces(UUID id, PointAccesCreate dto) {
        PointAccesEntity entity = pointAccesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Point d'accès introuvable"));
        mapper.updateEntity(dto, entity);
        return mapper.toDto(pointAccesRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletePointAcces(UUID id) {
        if (!pointAccesRepository.existsById(id)) throw new ResourceNotFoundException("Point d'accès introuvable");
        pointAccesRepository.deleteById(id);
    }

    private void validateOneOfConstraint(UUID patrimoineId, UUID entrepriseId) {
        if ((patrimoineId == null && entrepriseId == null) || (patrimoineId != null && entrepriseId != null)) {
            throw new BusinessException("Une géolocalisation doit cibler SOIT un patrimoine, SOIT une entreprise.");
        }

        if (patrimoineId != null) {

            if (geolocalisationRepository.existsByPatrimoineId(patrimoineId)) {
                throw new BusinessException("Le patrimoine avec l'ID " + patrimoineId + " possède déjà une géolocalisation.");
            }

            try {
                patrimoineClient.getPatrimoineById(patrimoineId);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException("Le patrimoine avec l'ID " + patrimoineId + " est introuvable dans ms-patrimoine.");
            } catch (FeignException e) {
                throw new BusinessException("Le service ms-patrimoine est temporairement indisponible ou a renvoyé une erreur : " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException("Erreur inattendue lors de la vérification du patrimoine : " + e.getMessage(), e);
            }
        }
    }

    public Point createSafePoint(Double lat, Double lon) {
        if (lat == null || lon == null) {
            throw new IllegalArgumentException("Les coordonnées latitude et longitude ne peuvent pas être nulles.");
        }

        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Coordonnées géographiques hors limites (Lat: [-90,90], Lon: [-180,180]).");
        }

        // Création JTS : Longitude (X) puis Latitude (Y)
        return gf.createPoint(new Coordinate(lon, lat));
    }

    // Outil interne de calcul pour valoriser la propriété distanceKm du DTO de retour
    private double calculerDistanceBrute(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return earthRadius * (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
    }

}

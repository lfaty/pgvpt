package com.pgvpt.service.impl;

import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.GeolocalisationEntity;
import com.pgvpt.repository.GeolocalisationRepository;
import com.pgvpt.repository.QuartierRepository;
import com.pgvpt.repository.VillageRepository;
import com.pgvpt.repository.VillageRepository;
import com.pgvpt.repository.ZoneTouristiqueRepository;
import com.pgvpt.service.GeolocalisationService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GeolocalisationServiceImpl implements GeolocalisationService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    private final GeolocalisationRepository repository;
    private final ZoneTouristiqueRepository zoneTouristiqueRepository;
    private final VillageRepository villageRepository;
    private final QuartierRepository quartierRepository;

    @Override
    public GeolocalisationEntity create(GeolocalisationEntity geolocalisationEntity) {
        if (geolocalisationEntity.getPatrimoineId() != null
            && repository.existsByPatrimoineId(geolocalisationEntity.getPatrimoineId())) {
            throw new IllegalArgumentException("Le patrimoine possède déjà une géolocalisation");
        }

        // Résolution des relations JPA (squelettes MapStruct → entités managées)
        resolveRelations(geolocalisationEntity);

        return repository.save(geolocalisationEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public GeolocalisationEntity getById(UUID id) {
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Géolocalisation introuvable : " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public GeolocalisationEntity getByPatrimoineId(UUID patrimoineId) {
        return repository.findByPatrimoineId(patrimoineId).orElseThrow(() ->
                new ResourceNotFoundException("Aucune géolocalisation pour le patrimoine : " + patrimoineId));
    }

    @Override
    @Transactional(readOnly = true)
    public GeolocalisationEntity getByZoneTouristiqueId(UUID zoneTouristiqueId) {
        return repository.findByZoneTouristiqueId(zoneTouristiqueId).orElseThrow(() ->
                new ResourceNotFoundException("Aucune géolocalisation pour la zone : " + zoneTouristiqueId));
    }

    @Override
    public GeolocalisationEntity update(UUID id, GeolocalisationEntity update) {
        GeolocalisationEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Géolocalisation introuvable : " + id));

        // Résolution des relations JPA (squelettes MapStruct → entités managées)
        resolveRelations(update);

        // Patch partiel : on ne met à jour que les champs fournis
        if (update.getLatitude() != null) existing.setLatitude(update.getLatitude());
        if (update.getLongitude() != null) existing.setLongitude(update.getLongitude());
        if (update.getAltitude() != null) existing.setAltitude(update.getAltitude());
        if (update.getPrecisionMetres() != null) existing.setPrecisionMetres(update.getPrecisionMetres());
        if (update.getAdresse() != null) existing.setAdresse(update.getAdresse());
        if (update.getLieuDit() != null) existing.setLieuDit(update.getLieuDit());
        if (update.getRepere() != null) existing.setRepere(update.getRepere());
        if (update.getSystemeReference() != null) existing.setSystemeReference(update.getSystemeReference());
        if (update.getCodeEpsg() != null) existing.setCodeEpsg(update.getCodeEpsg());
        if (update.getSource() != null) existing.setSource(update.getSource());
        if (update.getDateAcquisition() != null) existing.setDateAcquisition(update.getDateAcquisition());
        if (update.getMethodeAcquisition() != null) existing.setMethodeAcquisition(update.getMethodeAcquisition());
        if (update.getNiveauFiabilite() != null) existing.setNiveauFiabilite(update.getNiveauFiabilite());
        if (update.getZoneTouristique() != null) existing.setZoneTouristique(update.getZoneTouristique());
        if (update.getVillage() != null) existing.setVillage(update.getVillage());
        if (update.getQuartier() != null) existing.setQuartier(update.getQuartier());

        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Géolocalisation introuvable : " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GeolocalisationEntity> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GeolocalisationEntity> findNearby(double latitude, double longitude, double rayonKm, Pageable pageable) {
        // Filtrage en mémoire avec la formule de Haversine
        // (Pour une volumétrie importante, il faudra envisager PostGIS)
        List<GeolocalisationEntity> allGeos = repository.findAll();

        List<GeolocalisationEntity> filtered = allGeos.stream()
            .filter(g -> g.getLatitude() != null && g.getLongitude() != null)
            .filter(g -> calculateDistance(latitude, longitude, g.getLatitude(), g.getLongitude()) <= rayonKm)
            .sorted(Comparator.comparingDouble(
                g -> calculateDistance(latitude, longitude, g.getLatitude(), g.getLongitude())))
            .toList();

        // Pagination manuelle
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());

        List<GeolocalisationEntity> pageContent = start >= filtered.size()
            ? List.of()
            : filtered.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GeolocalisationEntity> findInBoundingBox(double minLat, double minLon, double maxLat, double maxLon, Pageable pageable) {
        return repository.findInBoundingBox(minLat, minLon, maxLat, maxLon, pageable);
    }

    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    private void resolveRelations(GeolocalisationEntity entity) {
        if (entity.getZoneTouristique() != null) {
            if (entity.getZoneTouristique().getId() != null) {
                entity.setZoneTouristique(zoneTouristiqueRepository.findById(entity.getZoneTouristique().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Zone touristique introuvable avec l'ID: " + entity.getZoneTouristique().getId())));
            } else {
                entity.setZoneTouristique(null);
            }
        }

        if (entity.getVillage() != null) {
            if (entity.getVillage().getId() != null) {
                entity.setVillage(villageRepository.findById(entity.getVillage().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Village introuvable avec l'ID: " + entity.getVillage().getId())));
            } else {
                entity.setVillage(null);
            }
        }

        if (entity.getQuartier() != null) {
            if (entity.getQuartier().getId() != null) {
                entity.setQuartier(quartierRepository.findById(entity.getQuartier().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Quartier introuvable avec l'ID: " + entity.getQuartier().getId())));
            } else {
                entity.setQuartier(null);
            }
        }
    }
}

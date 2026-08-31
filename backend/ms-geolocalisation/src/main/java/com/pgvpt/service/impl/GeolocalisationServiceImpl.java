package com.pgvpt.service.impl;

import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.GeolocalisationEntity;
import com.pgvpt.repository.GeolocalisationRepository;
import com.pgvpt.service.GeolocalisationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class GeolocalisationServiceImpl implements GeolocalisationService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    private final GeolocalisationRepository repository;


    @Override
    public GeolocalisationEntity create(GeolocalisationEntity geolocalisationEntity) {

        if (repository.existsByPatrimoineId(geolocalisationEntity.getPatrimoineId())) {
            throw new IllegalArgumentException("Le patrimoine possède déjà une géolocalisation");
        }

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
    public GeolocalisationEntity getByZoneTouristiqueId(UUID zoneTouristiqueId) {


        return repository.findByZoneTouristiqueId(zoneTouristiqueId).orElseThrow(() ->
                new ResourceNotFoundException("Aucune géolocalisation pour la zone : " + zoneTouristiqueId));
    }

    @Override
    public GeolocalisationEntity update(UUID id, GeolocalisationEntity geolocalisationEntity) {

        GeolocalisationEntity entity = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Géolocalisation introuvable : " + id));

        entity.setPatrimoineId(geolocalisationEntity.getPatrimoineId());
        entity.setAltitude(geolocalisationEntity.getAltitude());
        entity.setLongitude(geolocalisationEntity.getLongitude());
        entity.setPrecisionMetres(geolocalisationEntity.getPrecisionMetres());
        entity.setAdresse(geolocalisationEntity.getAdresse());
        entity.setQuartier(geolocalisationEntity.getQuartier());
        entity.setVillage(geolocalisationEntity.getVillage());
        entity.setLieuDit(geolocalisationEntity.getLieuDit());
        entity.setZoneTouristique(geolocalisationEntity.getZoneTouristique());
        entity.setRepere(geolocalisationEntity.getRepere());
        entity.setSource(geolocalisationEntity.getSource());

        return repository.save(entity);
    }

    @Override
    public void delete(UUID id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Géolocalisation introuvable : " + id);
        }

        repository.deleteById(id);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<GeolocalisationEntity> findByRegion(String region) {
//
//        return repository.findByRegionIgnoreCase(region);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GeolocalisationEntity> findByCommune(String commune) {
//
//        return repository.findByCommuneIgnoreCase(commune);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GeolocalisationEntity> findByDepartement(String departement) {
//
//        return repository.findByDepartementIgnoreCase(departement);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GeolocalisationEntity> findByRegionAndCommune(String region, String commune) {
//
//        return repository.findByRegionIgnoreCaseAndCommuneIgnoreCase(region, commune);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GeolocalisationEntity> findByRegionAndDepartement(String region, String departement) {
//
//        return repository.findByRegionIgnoreCaseAndDepartementIgnoreCase(region, departement);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GeolocalisationEntity> findByDepartementAndCommune(String departement, String commune) {
//
//        return repository.findByDepartementIgnoreCaseAndCommuneIgnoreCase(departement, commune);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<GeolocalisationEntity> findNearby(double latitude, double longitude, double rayonKm) {
//
//        return repository.findAll().stream()
//                .filter(g -> {double distance =
//                        calculateDistance(latitude, longitude, g.getLatitude(), g.getLongitude());
//
//                    return distance <= rayonKm;
//                })
//                .sorted(Comparator.comparingDouble(
//                                g -> calculateDistance(latitude, longitude, g.getLatitude(), g.getLongitude()))
//                ).toList();
//    }
//
//    @Override
//    public double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
//
//        double latDistance = Math.toRadians(latitude2 - latitude1);
//
//        double lonDistance = Math.toRadians(longitude2 - longitude1);
//
//        double a =
//                Math.sin(latDistance / 2)
//                        * Math.sin(latDistance / 2)
//                        + Math.cos(Math.toRadians(latitude1))
//                        * Math.cos(Math.toRadians(latitude2))
//                        * Math.sin(lonDistance / 2)
//                        * Math.sin(lonDistance / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return EARTH_RADIUS_KM * c;
//    }
}

package com.pgvpt.service;

import com.pgvpt.model.GeolocalisationEntity;

import java.util.List;
import java.util.UUID;

//Methode 2 : Methode moderne avec contrat-firts
public interface GeolocalisationService {

    GeolocalisationEntity create(GeolocalisationEntity geolocalisationEntity);

    GeolocalisationEntity getById(UUID id);

    GeolocalisationEntity getByPatrimoineId(UUID patrimoineId);

    GeolocalisationEntity getByZoneTouristiqueId(UUID zoneTouristiqueId);

    GeolocalisationEntity update(UUID id, GeolocalisationEntity geolocalisationEntity);

    void delete(UUID id);

//    List<GeolocalisationEntity> findByRegion(String region);
//
//    List<GeolocalisationEntity> findByCommune(String commune);
//
//    List<GeolocalisationEntity> findByDepartement(String departement);
//
//    List<GeolocalisationEntity> findByRegionAndCommune(String region, String commune);
//
//    List<GeolocalisationEntity> findByRegionAndDepartement(String region, String departement);
//    List<GeolocalisationEntity> findByDepartementAndCommune(String departement, String commune);
//
//    List<GeolocalisationEntity> findNearby(double latitude, double longitude, double rayonKm);
//
//    double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2);
}

//Methode 1 : Methode classique sans contrat-firts
//import com.pgvpt.dto.GeolocalisationCreateDto;
//import com.pgvpt.dto.GeolocalisationDto;
//
//import java.util.List;
//import java.util.UUID;

//public interface GeolocalisationService {
//
//    GeolocalisationDto create(GeolocalisationCreateDto request);
//
//    GeolocalisationDto getById(UUID id);
//
//    GeolocalisationDto getByPatrimoineId(UUID patrimoineId);
//
//    GeolocalisationDto update(UUID id, GeolocalisationCreateDto request);
//
//    void delete(UUID id);
//
//    List<GeolocalisationDto> findByRegion(String region);
//
//    List<GeolocalisationDto> findByCommune(String commune);
//
//    List<GeolocalisationDto> findNearby(double latitude, double longitude, double rayonKm);
//
//    double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2);
//}

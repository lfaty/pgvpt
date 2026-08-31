package com.pgvpt.controller;

import com.dev.pgvpt.api.model.Geolocalisation;
import com.dev.pgvpt.api.model.GeolocalisationCreate;
import com.dev.pgvpt.api.model.GeolocalisationUpdate;
import com.dev.pgvpt.api.model.PageGeolocalisation;
import com.pgvpt.mapper.GeolocalisationApiMapper;
import com.pgvpt.service.GeolocalisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import com.dev.pgvpt.api.GeolocalisationsApi;

//Methode 2 : Methode moderne avec contract-first
@RestController
@RequiredArgsConstructor
public class GeolocalisationController implements GeolocalisationsApi {

    private final GeolocalisationService service;

    private final GeolocalisationApiMapper mapper;

    @Override
    public ResponseEntity<Geolocalisation> createGeolocalisation(GeolocalisationCreate request) {

        var entity = mapper.toEntity(request);
        var created = service.create(entity);
        return ResponseEntity.status(201).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Geolocalisation>
    getGeolocalisation(UUID id) {

        return ResponseEntity.ok(mapper.toApi(service.getById(id)));
    }

    @Override
    public ResponseEntity<PageGeolocalisation> getGeolocalisations(Integer page, Integer size, String departement, String commune) {
        return null;
    }

    @Override
    public ResponseEntity<Geolocalisation> updateGeolocalisation(UUID id, GeolocalisationUpdate request) {

        var entity = mapper.toEntity(request);

        var updated = service.update(id, entity);

        return ResponseEntity.ok(mapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Void> deleteGeolocalisation(UUID id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<Geolocalisation> getPatrimoineGeolocalisation(UUID patrimoineId) {
        return null;
    }

    @Override
    public ResponseEntity<Geolocalisation> updatePatrimoineGeolocalisation(UUID patrimoineId, GeolocalisationUpdate geolocalisationUpdate) {
        return null;
    }

////    @GetMapping("/geolocalisations/recherche/region/{region}")
//    public ResponseEntity<List<Geolocalisation>> findByRegion(@PathVariable String region) {
//
//        List<Geolocalisation> dtos = service.findByRegion(region)
//                .stream()
//                .map(mapper::toApi) // Convertit chaque entité en DTO
//                .toList();
//
//        return ResponseEntity.ok(dtos);
//    }
//
//
////    @GetMapping("/geolocalisations/recherche/commune/{commune}")
//    public ResponseEntity<List<Geolocalisation>> findByCommune(@PathVariable String commune) {
//
//        List<Geolocalisation> dtos = service.findByCommune(commune)
//                .stream()
//                .map(mapper::toApi) // Convertit chaque entité en DTO
//                .toList();
//
//        return ResponseEntity.ok(dtos);
//    }
//
////    @GetMapping("/geolocalisations/recherche/rayon")
//    public ResponseEntity<List<Geolocalisation>> findNearby(
//            @RequestParam double latitude,
//            @RequestParam double longitude,
//            @RequestParam double rayonKm) {
//
//        List<Geolocalisation> dtos = service.findNearby(latitude, longitude, rayonKm)
//                .stream()
//                .map(mapper::toApi) // Convertit chaque entité en DTO
//                .toList();
//
//        return ResponseEntity.ok(dtos);
//    }
//
////    @GetMapping("/geolocalisations/distance")
//    public ResponseEntity<Double> calculateDistance(
//            @RequestParam double latitudeDepart,
//            @RequestParam double longitudeDepart,
//            @RequestParam double latitudeArrivee,
//            @RequestParam double longitudeArrivee) {
//
//        return ResponseEntity.ok(
//                service.calculateDistance(
//                        latitudeDepart,
//                        longitudeDepart,
//                        latitudeArrivee,
//                        longitudeArrivee
//                )
//        );
//    }
//


}

//Methode 1 : Methode classique sans contract-first
//
//import com.pgvpt.dto.GeolocalisationCreateDto;
//import com.pgvpt.dto.GeolocalisationDto;
//import com.pgvpt.service.GeolocalisationService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/v1")
//@RequiredArgsConstructor
//public class GeolocalisationController {
//
//    private final GeolocalisationService service;
//
//    @PostMapping("/geolocalisations")
//    public ResponseEntity<GeolocalisationDto> create(
//            @Valid @RequestBody
//            GeolocalisationCreateDto request) {
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(service.create(request));
//    }
//
//    @GetMapping("/geolocalisations/{id}")
//    public ResponseEntity<GeolocalisationDto> getById(
//            @PathVariable UUID id) {
//
//        return ResponseEntity.ok(
//                service.getById(id)
//        );
//    }
//
//    @GetMapping("/patrimoines/{patrimoineId}/geolocalisation")
//    public ResponseEntity<GeolocalisationDto>
//    getByPatrimoineId(
//            @PathVariable UUID patrimoineId) {
//
//        return ResponseEntity.ok(
//                service.getByPatrimoineId(patrimoineId)
//        );
//    }
//
//    @PutMapping("/geolocalisations/{id}")
//    public ResponseEntity<GeolocalisationDto> update(
//            @PathVariable UUID id,
//            @Valid @RequestBody
//            GeolocalisationCreateDto request) {
//
//        return ResponseEntity.ok(
//                service.update(id, request)
//        );
//    }
//
//    @DeleteMapping("/geolocalisations/{id}")
//    public ResponseEntity<Void> delete(
//            @PathVariable UUID id) {
//
//        service.delete(id);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/geolocalisations/recherche/region/{region}")
//    public ResponseEntity<List<GeolocalisationDto>>
//    findByRegion(
//            @PathVariable String region) {
//
//        return ResponseEntity.ok(
//                service.findByRegion(region)
//        );
//    }
//
//    @GetMapping("/geolocalisations/recherche/commune/{commune}")
//    public ResponseEntity<List<GeolocalisationDto>>
//    findByCommune(
//            @PathVariable String commune) {
//
//        return ResponseEntity.ok(
//                service.findByCommune(commune)
//        );
//    }
//
//    @GetMapping("/geolocalisations/recherche/rayon")
//    public ResponseEntity<List<GeolocalisationDto>>
//    findNearby(
//            @RequestParam double latitude,
//            @RequestParam double longitude,
//            @RequestParam double rayonKm) {
//
//        return ResponseEntity.ok(
//                service.findNearby(
//                        latitude,
//                        longitude,
//                        rayonKm
//                )
//        );
//    }
//
//    @GetMapping("/geolocalisations/distance")
//    public ResponseEntity<Double> calculateDistance(
//            @RequestParam double latitudeDepart,
//            @RequestParam double longitudeDepart,
//            @RequestParam double latitudeArrivee,
//            @RequestParam double longitudeArrivee) {
//
//        return ResponseEntity.ok(
//                service.calculateDistance(
//                        latitudeDepart,
//                        longitudeDepart,
//                        latitudeArrivee,
//                        longitudeArrivee
//                )
//        );
//    }
//}



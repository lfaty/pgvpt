package com.pgvpt.controller;

import com.pgvpt.api.GeolocalisationsApi;
import com.pgvpt.dto.*;
import com.pgvpt.service.GeolocalisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GeolocalisationController implements GeolocalisationsApi {

    private final GeolocalisationService service;

    @Override
    public ResponseEntity<Geolocalisation> createGeolocalisation(GeolocalisationCreate geolocalisationCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(geolocalisationCreate));
    }

    @Override
    public ResponseEntity<Void> deleteGeolocalisation(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Geolocalisation> getGeolocalisation(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<PageGeolocalisation> getGeolocalisations(Integer page, Integer size, String departement, String commune) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @Override
    public ResponseEntity<Geolocalisation> getPatrimoineGeolocalisation(UUID patrimoineId) {
        return ResponseEntity.ok(service.getByPatrimoineId(patrimoineId));
    }

    @Override
    public ResponseEntity<Geolocalisation> patchGeolocalisation(UUID id, GeolocalisationUpdate geolocalisationUpdate) {
        return ResponseEntity.ok(service.patch(id, geolocalisationUpdate));
    }

    @Override
    public ResponseEntity<Geolocalisation> patchPatrimoineGeolocalisation(UUID patrimoineId, GeolocalisationUpdate geolocalisationUpdate) {
        return ResponseEntity.ok(service.patchByPatrimoineId(patrimoineId, geolocalisationUpdate));
    }

    @Override
    public ResponseEntity<Geolocalisation> updateGeolocalisation(UUID id, GeolocalisationUpdate geolocalisationUpdate) {
        return ResponseEntity.ok(service.update(id, geolocalisationUpdate));
    }

    @Override
    public ResponseEntity<Geolocalisation> updatePatrimoineGeolocalisation(UUID patrimoineId, GeolocalisationUpdate geolocalisationUpdate) {
        Geolocalisation current = service.getByPatrimoineId(patrimoineId);
        return ResponseEntity.ok(service.update(current.getId(), geolocalisationUpdate));
    }

}

package com.pgvpt.controller;

import com.dev.pgvpt.api.GeolocalisationsApi;
import com.dev.pgvpt.api.model.Geolocalisation;
import com.dev.pgvpt.api.model.GeolocalisationCreate;
import com.dev.pgvpt.api.model.GeolocalisationUpdate;
import com.dev.pgvpt.api.model.PageGeolocalisation;
import com.pgvpt.mapper.GeolocalisationApiMapper;
import com.pgvpt.model.GeolocalisationEntity;
import com.pgvpt.service.GeolocalisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
    public ResponseEntity<Geolocalisation> getGeolocalisation(UUID id) {
        return ResponseEntity.ok(mapper.toApi(service.getById(id)));
    }

    @Override
    public ResponseEntity<PageGeolocalisation> getGeolocalisations(Integer page, Integer size, String departement, String commune) {
        Page<GeolocalisationEntity> result = service.getAll(PageRequest.of(page, size));

        PageGeolocalisation response = new PageGeolocalisation(
            result.getContent().stream().map(mapper::toApi).toList(),
            result.getNumber(),
            result.getSize(),
            result.getTotalElements(),
            result.getTotalPages(),
            result.isFirst(),
            result.isLast()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Geolocalisation> updateGeolocalisation(UUID id, GeolocalisationUpdate request) {
        var entity = mapper.toEntity(request);
        var updated = service.update(id, entity);
        return ResponseEntity.ok(mapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Geolocalisation> patchGeolocalisation(UUID id, GeolocalisationUpdate request) {
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
        return ResponseEntity.ok(mapper.toApi(service.getByPatrimoineId(patrimoineId)));
    }

    @Override
    public ResponseEntity<Geolocalisation> updatePatrimoineGeolocalisation(UUID patrimoineId, GeolocalisationUpdate geolocalisationUpdate) {
        // Récupère la géolocalisation existante par patrimoineId, puis la met à jour
        GeolocalisationEntity existing = service.getByPatrimoineId(patrimoineId);
        var entity = mapper.toEntity(geolocalisationUpdate);
        var updated = service.update(existing.getId(), entity);
        return ResponseEntity.ok(mapper.toApi(updated));
    }

    @Override
    public ResponseEntity<Geolocalisation> patchPatrimoineGeolocalisation(UUID patrimoineId, GeolocalisationUpdate geolocalisationUpdate) {
        GeolocalisationEntity existing = service.getByPatrimoineId(patrimoineId);
        var entity = mapper.toEntity(geolocalisationUpdate);
        var updated = service.update(existing.getId(), entity);
        return ResponseEntity.ok(mapper.toApi(updated));
    }
}

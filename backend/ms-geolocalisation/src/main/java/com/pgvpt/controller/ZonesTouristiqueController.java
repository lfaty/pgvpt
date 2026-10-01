package com.pgvpt.controller;

import com.pgvpt.api.*;
import com.pgvpt.dto.PageZoneTouristique;
import com.pgvpt.dto.ZoneTouristique;
import com.pgvpt.dto.ZoneTouristiqueCreate;
import com.pgvpt.dto.ZoneTouristiqueUpdate;
import com.pgvpt.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class ZonesTouristiqueController implements ZoneTouristiqueApi {

    private final ZoneService service;

    @Override
    public ResponseEntity<ZoneTouristique> createZoneTouristique(ZoneTouristiqueCreate zoneTouristiqueCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTour(zoneTouristiqueCreate));
    }

    @Override
    public ResponseEntity<Void> deleteZoneTouristique(UUID id) {
        service.deleteTour(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ZoneTouristique> getZoneTouristique(UUID id) {
        return ResponseEntity.ok(service.getByIdTour(id));
    }

    @Override
    public ResponseEntity<PageZoneTouristique> getZonesTouristiques(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllTour(pageable));
    }

    @Override
    public ResponseEntity<ZoneTouristique> updateZoneTouristique(UUID id, ZoneTouristiqueUpdate zoneTouristiqueUpdate) {
        return ResponseEntity.ok(service.updateTour(id, zoneTouristiqueUpdate));
    }
}


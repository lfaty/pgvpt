package com.pgvpt.controller;

import com.dev.pgvpt.api.ZoneGeographiqueApi;
import com.dev.pgvpt.api.model.PageZoneGeographique;
import com.dev.pgvpt.api.model.ZoneGeographique;
import com.dev.pgvpt.api.model.ZoneGeographiqueCreate;
import com.dev.pgvpt.api.model.ZoneGeographiqueUpdate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


public class ZoneGeographiqueController implements ZoneGeographiqueApi {

    @Override
    public ResponseEntity<ZoneGeographique> createZoneGeographique(ZoneGeographiqueCreate zoneGeographiqueCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteZoneGeographique(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<ZoneGeographique> getZoneGeographique(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<PageZoneGeographique> getZonesGeographiques(Integer page, Integer size) {
        return null;
    }


    @Override
    public ResponseEntity<ZoneGeographique> updateZoneGeographique(UUID id, ZoneGeographiqueUpdate zoneGeographiqueUpdate) {
        return null;
    }
}

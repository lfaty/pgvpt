package com.pgvpt.controller;

import com.dev.pgvpt.api.ZoneTouristiqueApi;
import com.dev.pgvpt.api.model.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class ZonesTouristiqueController implements ZoneTouristiqueApi {
    @Override
    public ResponseEntity<ZoneTouristique> createZoneTouristique(ZoneTouristiqueCreate zoneTouristiqueCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteZoneTouristique(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<ZoneTouristique> getZoneTouristique(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<PageZoneTouristique> getZonesTouristiques(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<ZoneTouristique> updateZoneTouristique(UUID id, ZoneTouristiqueUpdate zoneTouristiqueUpdate) {
        return null;
    }
}

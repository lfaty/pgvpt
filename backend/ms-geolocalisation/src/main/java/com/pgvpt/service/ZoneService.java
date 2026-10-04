package com.pgvpt.service;

import com.pgvpt.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface ZoneService {
    PageZoneGeographique getAllGeo(Pageable pageable);

    ZoneGeographique getByIdGeo(UUID id);

    ZoneGeographique createGeo(ZoneGeographiqueCreate dto);

    ZoneGeographique updateGeo(UUID id, ZoneGeographiqueUpdate dto);

    void deleteGeo(UUID id);

    PageZoneTouristique getAllTour(Pageable pageable);

    ZoneTouristique getByIdTour(UUID id);

    ZoneTouristique createTour(ZoneTouristiqueCreate dto);

    ZoneTouristique updateTour(UUID id, ZoneTouristiqueUpdate dto);

    void deleteTour(UUID id);
}

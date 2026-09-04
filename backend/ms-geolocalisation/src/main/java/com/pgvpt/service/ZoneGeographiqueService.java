package com.pgvpt.service;

import com.pgvpt.model.ZoneGeographiqueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface ZoneGeographiqueService {

    ZoneGeographiqueEntity createZoneGeographique(ZoneGeographiqueEntity zoneGeographique, Set<UUID> regionIds);

    ZoneGeographiqueEntity getZoneGeographiqueById(UUID id);

    Page<ZoneGeographiqueEntity> getZonesGeographiques(Pageable pageable);

    ZoneGeographiqueEntity updateZoneGeographique(UUID id, ZoneGeographiqueEntity zoneGeographiqueUpdate, Set<UUID> regionIds);

    void deleteZoneGeographique(UUID id);
}

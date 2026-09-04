package com.pgvpt.service;

import com.pgvpt.model.ZoneTouristiqueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ZoneTouristiqueService {

    ZoneTouristiqueEntity createZoneTouristique(ZoneTouristiqueEntity zoneTouristique);

    ZoneTouristiqueEntity getZoneTouristiqueById(UUID id);

    Page<ZoneTouristiqueEntity> getZonesTouristiques(Pageable pageable);

    ZoneTouristiqueEntity updateZoneTouristique(UUID id, ZoneTouristiqueEntity zoneTouristiqueUpdate);

    void deleteZoneTouristique(UUID id);
}

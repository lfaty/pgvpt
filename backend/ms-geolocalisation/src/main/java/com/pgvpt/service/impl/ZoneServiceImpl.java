package com.pgvpt.service.impl;

import com.pgvpt.dto.*;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.ZoneMapper;
import com.pgvpt.model.*;
import com.pgvpt.repository.RegionRepository;
import com.pgvpt.repository.ZoneGeographiqueRepository;
import com.pgvpt.repository.ZoneTouristiqueRepository;
import com.pgvpt.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final ZoneGeographiqueRepository zoneGeographiqueRepository;
    private final ZoneTouristiqueRepository zoneTouristiqueRepository;
    private final RegionRepository regionRepository;
    private final ZoneMapper mapper;

    @Override
    public PageZoneGeographique getAllGeo(Pageable pageable) {
        Page<ZoneGeographiqueEntity> page = zoneGeographiqueRepository.findAll(pageable);
        return new PageZoneGeographique()
                .content(page.getContent().stream().map(mapper::toDtoGeo).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public ZoneGeographique getByIdGeo(UUID id) {
        return zoneGeographiqueRepository.findById(id).map(mapper::toDtoGeo)
                .orElseThrow(() -> new ResourceNotFoundException("Zone introuvable"));
    }

    @Override
    @Transactional
    public ZoneGeographique createGeo(ZoneGeographiqueCreate dto) {
        // Vérification unicité du code
        if (dto.getCode() != null && zoneGeographiqueRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Une zone géographique avec le code '" + dto.getCode() + "' existe déjà.");
        }
        // Vérification unicité du nom
        if (dto.getNom() != null && zoneGeographiqueRepository.existsByNom(dto.getNom())) {
            throw new IllegalArgumentException(
                    "Une zone géographique avec le nom '" + dto.getNom() + "' existe déjà.");
        }

        ZoneGeographiqueEntity entity = mapper.toEntityGeo(dto);
        ZoneGeographiqueEntity saved = zoneGeographiqueRepository.save(entity);

        Set<UUID> regionIds = dto.getRegionIds();
        if (regionIds != null && !regionIds.isEmpty()) {
            List<ZoneGeographiqueRegionEntity> links = buildRegionLinks(saved, regionIds);
            saved.getRegionLinks().addAll(links);
            saved = zoneGeographiqueRepository.save(saved);
        }

        return mapper.toDtoGeo(saved);
    }

    @Override
    @Transactional
    public ZoneGeographique updateGeo(UUID id, ZoneGeographiqueUpdate dto) {
        // Récupération de l'entité existante
        ZoneGeographiqueEntity entity = zoneGeographiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone géographique introuvable"));

        // Vérification de l'unicité du code (en excluant l'entité elle-même)
        if (dto.getCode() != null && zoneGeographiqueRepository.existsByCodeAndIdNot(dto.getCode(), id)) {
            throw new IllegalArgumentException("Une zone géographique avec le code '" + dto.getCode() + "' existe déjà.");
        }

        // Vérification de l'unicité du nom (en excluant l'entité elle-même)
        if (dto.getNom() != null && zoneGeographiqueRepository.existsByNomAndIdNot(dto.getNom(), id)) {
            throw new IllegalArgumentException("Une zone géographique avec le nom '" + dto.getNom() + "' existe déjà.");
        }

        // Mise à jour des champs de l'entité via le mapper
        mapper.updateEntityGeo(dto, entity);

        // Gestion de la relation de jointure
        Set<UUID> regionIds = dto.getRegionIds();
        if (regionIds != null) {
            entity.getRegionLinks().clear();
            if (!regionIds.isEmpty()) {
                List<ZoneGeographiqueRegionEntity> links = buildRegionLinks(entity, regionIds);
                entity.getRegionLinks().addAll(links);
            }
        }
        ZoneGeographiqueEntity saved = zoneGeographiqueRepository.save(entity);

        return mapper.toDtoGeo(saved);
    }

    @Override
    public void deleteGeo(UUID id) {
        if (!zoneGeographiqueRepository.existsById(id))
            throw new ResourceNotFoundException("Zone geographique introuvable");
        zoneGeographiqueRepository.deleteById(id);
    }

    @Override
    public PageZoneTouristique getAllTour(Pageable pageable) {
        Page<ZoneTouristiqueEntity> page = zoneTouristiqueRepository.findAll(pageable);
        return new PageZoneTouristique()
                .content(page.getContent().stream().map(mapper::toDtoTour).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public ZoneTouristique getByIdTour(UUID id) {
        return zoneTouristiqueRepository.findById(id).map(mapper::toDtoTour)
                .orElseThrow(() -> new ResourceNotFoundException("Zone touristique introuvable"));
    }

    @Override
    public ZoneTouristique createTour(ZoneTouristiqueCreate dto) {
        ZoneTouristiqueEntity entity = mapper.toEntityTour(dto);
        return mapper.toDtoTour(zoneTouristiqueRepository.save(entity));
    }

    @Override
    public ZoneTouristique updateTour(UUID id, ZoneTouristiqueUpdate dto) {
        ZoneTouristiqueEntity entity = zoneTouristiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone touristique  introuvable"));

        mapper.updateEntityTour(dto, entity);
        return mapper.toDtoTour(zoneTouristiqueRepository.save(entity));
    }

    @Override
    public void deleteTour(UUID id) {
        if (!zoneTouristiqueRepository.existsById(id))
            throw new ResourceNotFoundException("Zone touristique introuvable");
        zoneTouristiqueRepository.deleteById(id);
    }

    private List<ZoneGeographiqueRegionEntity> buildRegionLinks(ZoneGeographiqueEntity zone, Set<UUID> regionIds) {
        return regionIds.stream()
                .map(regionId -> {
                    RegionEntity region = regionRepository.getReferenceById(regionId);
                    ZoneGeographiqueRegionEntity link = new ZoneGeographiqueRegionEntity();
                    link.setZoneGeographique(zone);
                    link.setRegion(region);
                    return link; }
                ).toList();
    }
}

package com.pgvpt.service.impl;

import com.pgvpt.dto.*;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.ZoneMapper;
import com.pgvpt.model.*;
import com.pgvpt.repository.*;
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
    private final DepartementRepository departementRepository;
    private final CommuneRepository communeRepository;
    private final VillageRepository villageRepository;
    private final GeolocalisationRepository geolocalisationRepository;
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
        if (entity.getRegionLinks() == null) {
            entity.setRegionLinks(new ArrayList<>());
        }
        ZoneGeographiqueEntity saved = zoneGeographiqueRepository.save(entity);

        Set<UUID> regionIds = dto.getRegionIds();
        if (regionIds != null && !regionIds.isEmpty()) {
            List<ZoneGeographiqueRegionEntity> links = buildRegionLinks(saved, regionIds);
            if (saved.getRegionLinks() == null) {
                saved.setRegionLinks(new ArrayList<>());
            }
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
            if (entity.getRegionLinks() == null) {
                entity.setRegionLinks(new ArrayList<>());
            } else {
                entity.getRegionLinks().clear();
            }
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
        if (zoneTouristiqueRepository.existsByZoneGeographique_Id(id)) {
            throw new com.pgvpt.exception.BusinessException("Impossible de supprimer la zone géographique car des zones touristiques y sont rattachées.");
        }
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
        if (dto.getCode() != null && zoneTouristiqueRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Une zone touristique avec le code '" + dto.getCode() + "' existe déjà.");
        }
        if (dto.getNom() != null && zoneTouristiqueRepository.existsByNom(dto.getNom())) {
            throw new IllegalArgumentException("Une zone touristique avec le nom '" + dto.getNom() + "' existe déjà.");
        }

        ZoneTouristiqueEntity entity = mapper.toEntityTour(dto);

        if (dto.getZoneGeographiqueId() != null) {
            ZoneGeographiqueEntity zoneGeo = zoneGeographiqueRepository.findById(dto.getZoneGeographiqueId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zone géographique introuvable avec l'ID : " + dto.getZoneGeographiqueId()));
            entity.setZoneGeographique(zoneGeo);
        } else {
            throw new IllegalArgumentException("L'identifiant de la zone géographique est obligatoire.");
        }

        if (dto.getDepartementId() != null) {
            DepartementEntity dept = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec l'ID : " + dto.getDepartementId()));
            entity.setDepartement(dept);
        }
        if (dto.getCommuneId() != null) {
            CommuneEntity commune = communeRepository.findById(dto.getCommuneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Commune introuvable avec l'ID : " + dto.getCommuneId()));
            entity.setCommune(commune);
        }
        if (dto.getVillageId() != null) {
            VillageEntity village = villageRepository.findById(dto.getVillageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Village introuvable avec l'ID : " + dto.getVillageId()));
            entity.setVillage(village);
        }

        return mapper.toDtoTour(zoneTouristiqueRepository.save(entity));
    }

    @Override
    public ZoneTouristique updateTour(UUID id, ZoneTouristiqueUpdate dto) {
        ZoneTouristiqueEntity entity = zoneTouristiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone touristique introuvable"));

        if (dto.getCode() != null && zoneTouristiqueRepository.existsByCodeAndIdNot(dto.getCode(), id)) {
            throw new IllegalArgumentException("Une zone touristique avec le code '" + dto.getCode() + "' existe déjà.");
        }
        if (dto.getNom() != null && zoneTouristiqueRepository.existsByNomAndIdNot(dto.getNom(), id)) {
            throw new IllegalArgumentException("Une zone touristique avec le nom '" + dto.getNom() + "' existe déjà.");
        }

        mapper.updateEntityTour(dto, entity);

        if (dto.getZoneGeographiqueId() != null) {
            ZoneGeographiqueEntity zoneGeo = zoneGeographiqueRepository.findById(dto.getZoneGeographiqueId())
                    .orElseThrow(() -> new ResourceNotFoundException("Zone géographique introuvable avec l'ID : " + dto.getZoneGeographiqueId()));
            entity.setZoneGeographique(zoneGeo);
        }
        if (dto.getDepartementId() != null) {
            DepartementEntity dept = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new ResourceNotFoundException("Département introuvable avec l'ID : " + dto.getDepartementId()));
            entity.setDepartement(dept);
        }
        if (dto.getCommuneId() != null) {
            CommuneEntity commune = communeRepository.findById(dto.getCommuneId())
                    .orElseThrow(() -> new ResourceNotFoundException("Commune introuvable avec l'ID : " + dto.getCommuneId()));
            entity.setCommune(commune);
        }
        if (dto.getVillageId() != null) {
            VillageEntity village = villageRepository.findById(dto.getVillageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Village introuvable avec l'ID : " + dto.getVillageId()));
            entity.setVillage(village);
        }

        return mapper.toDtoTour(zoneTouristiqueRepository.save(entity));
    }

    @Override
    public void deleteTour(UUID id) {
        if (!zoneTouristiqueRepository.existsById(id))
            throw new ResourceNotFoundException("Zone touristique introuvable");

        // Détacher automatiquement les géolocalisations rattachées à cette zone
        geolocalisationRepository.detachZoneTouristique(id);

        zoneTouristiqueRepository.deleteById(id);
    }

    private List<ZoneGeographiqueRegionEntity> buildRegionLinks(ZoneGeographiqueEntity zone, Set<UUID> regionIds) {
        return regionIds.stream()
                .map(regionId -> {
                    RegionEntity region = regionRepository.findById(regionId)
                            .orElseThrow(() -> new ResourceNotFoundException("Région introuvable avec l'ID : " + regionId));
                    ZoneGeographiqueRegionEntity link = new ZoneGeographiqueRegionEntity();
                    link.setZoneGeographique(zone);
                    link.setRegion(region);
                    return link;
                }).toList();
    }
}

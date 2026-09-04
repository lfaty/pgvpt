package com.pgvpt.service.impl;

import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.RegionEntity;
import com.pgvpt.model.ZoneGeographiqueEntity;
import com.pgvpt.model.ZoneGeographiqueRegionEntity;
import com.pgvpt.repository.RegionRepository;
import com.pgvpt.repository.ZoneGeographiqueRepository;
import com.pgvpt.service.ZoneGeographiqueService;
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
public class ZoneGeographiqueServiceImpl implements ZoneGeographiqueService {

    private final ZoneGeographiqueRepository zoneGeographiqueRepository;
    private final RegionRepository regionRepository;

    @Override
    public ZoneGeographiqueEntity createZoneGeographique(ZoneGeographiqueEntity zoneGeographique, Set<UUID> regionIds) {
        // Vérification unicité du code
        if (zoneGeographique.getCode() != null && zoneGeographiqueRepository.existsByCode(zoneGeographique.getCode())) {
            throw new IllegalArgumentException(
                "Une zone géographique avec le code '" + zoneGeographique.getCode() + "' existe déjà.");
        }
        // Vérification unicité du nom
        if (zoneGeographique.getNom() != null && zoneGeographiqueRepository.existsByNom(zoneGeographique.getNom())) {
            throw new IllegalArgumentException(
                "Une zone géographique avec le nom '" + zoneGeographique.getNom() + "' existe déjà.");
        }

        // Sauvegarde initiale pour obtenir l'ID (requis pour les entités de jointure)
        ZoneGeographiqueEntity saved = zoneGeographiqueRepository.save(zoneGeographique);

        // Création des entités de jointure avec les régions résolues
        List<ZoneGeographiqueRegionEntity> links = buildRegionLinks(saved, regionIds);
        saved.getRegionLinks().addAll(links);

        return zoneGeographiqueRepository.save(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ZoneGeographiqueEntity getZoneGeographiqueById(UUID id) {
        return zoneGeographiqueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Zone géographique non trouvée avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ZoneGeographiqueEntity> getZonesGeographiques(Pageable pageable) {
        return zoneGeographiqueRepository.findAll(pageable);
    }

    @Override
    public ZoneGeographiqueEntity updateZoneGeographique(UUID id, ZoneGeographiqueEntity zoneGeographiqueUpdate, Set<UUID> regionIds) {
        ZoneGeographiqueEntity existing = getZoneGeographiqueById(id);

        // Vérification unicité du code (en excluant l'entité courante)
        if (zoneGeographiqueUpdate.getCode() != null
            && !zoneGeographiqueUpdate.getCode().equals(existing.getCode())
            && zoneGeographiqueRepository.existsByCodeAndIdNot(zoneGeographiqueUpdate.getCode(), id)) {
            throw new IllegalArgumentException(
                "Une zone géographique avec le code '" + zoneGeographiqueUpdate.getCode() + "' existe déjà.");
        }
        // Vérification unicité du nom (en excluant l'entité courante)
        if (zoneGeographiqueUpdate.getNom() != null
            && !zoneGeographiqueUpdate.getNom().equals(existing.getNom())
            && zoneGeographiqueRepository.existsByNomAndIdNot(zoneGeographiqueUpdate.getNom(), id)) {
            throw new IllegalArgumentException(
                "Une zone géographique avec le nom '" + zoneGeographiqueUpdate.getNom() + "' existe déjà.");
        }

        // Application des champs modifiables (patch partiel)
        if (zoneGeographiqueUpdate.getCode() != null) existing.setCode(zoneGeographiqueUpdate.getCode());
        if (zoneGeographiqueUpdate.getNom() != null) existing.setNom(zoneGeographiqueUpdate.getNom());
        if (zoneGeographiqueUpdate.getDescription() != null) existing.setDescription(zoneGeographiqueUpdate.getDescription());
        if (zoneGeographiqueUpdate.getSuperficieKm2() != null) existing.setSuperficieKm2(zoneGeographiqueUpdate.getSuperficieKm2());
        if (zoneGeographiqueUpdate.getLatitudeCentre() != null) existing.setLatitudeCentre(zoneGeographiqueUpdate.getLatitudeCentre());
        if (zoneGeographiqueUpdate.getLongitudeCentre() != null) existing.setLongitudeCentre(zoneGeographiqueUpdate.getLongitudeCentre());
        if (zoneGeographiqueUpdate.getActif() != null) existing.setActif(zoneGeographiqueUpdate.getActif());

        // Mise à jour des régions si des IDs sont fournis
        // orphanRemoval=true supprime automatiquement les anciens liens lors du clear()
        if (regionIds != null && !regionIds.isEmpty()) {
            existing.getRegionLinks().clear();
            existing.getRegionLinks().addAll(buildRegionLinks(existing, regionIds));
        }

        return zoneGeographiqueRepository.save(existing);
    }

    @Override
    public void deleteZoneGeographique(UUID id) {
        if (!zoneGeographiqueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Zone géographique non trouvée avec l'ID: " + id);
        }
        zoneGeographiqueRepository.deleteById(id);
    }

    // ─────────────────────────── Méthodes privées ────────────────────────────

    /**
     * Résout les régions depuis le repository et crée les entités de jointure.
     * Lève une exception si une région est introuvable.
     */
    private List<ZoneGeographiqueRegionEntity> buildRegionLinks(ZoneGeographiqueEntity zone, Set<UUID> regionIds) {
        if (regionIds == null || regionIds.isEmpty()) {
            throw new IllegalArgumentException("Au moins une région est requise pour une zone géographique.");
        }
        List<ZoneGeographiqueRegionEntity> links = new ArrayList<>();
        for (UUID regionId : regionIds) {
            RegionEntity region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ResourceNotFoundException("Région non trouvée avec l'ID: " + regionId));
            links.add(ZoneGeographiqueRegionEntity.builder()
                .zoneGeographique(zone)
                .region(region)
                .build());
        }
        return links;
    }
}

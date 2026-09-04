package com.pgvpt.service.impl;

import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.model.*;
import com.pgvpt.repository.*;
import com.pgvpt.service.ZoneTouristiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ZoneTouristiqueServiceImpl implements ZoneTouristiqueService {

    private final ZoneTouristiqueRepository zoneTouristiqueRepository;
    private final ZoneGeographiqueRepository zoneGeographiqueRepository;
    private final DepartementRepository departementRepository;
    private final CommuneRepository communeRepository;
    private final VillageRepository villageRepository;

    @Override
    public ZoneTouristiqueEntity createZoneTouristique(ZoneTouristiqueEntity zoneTouristique) {
        // Vérification unicité du code
        if (zoneTouristique.getCode() != null && zoneTouristiqueRepository.existsByCode(zoneTouristique.getCode())) {
            throw new IllegalArgumentException(
                "Une zone touristique avec le code '" + zoneTouristique.getCode() + "' existe déjà.");
        }
        // Vérification unicité du nom
        if (zoneTouristique.getNom() != null && zoneTouristiqueRepository.existsByNom(zoneTouristique.getNom())) {
            throw new IllegalArgumentException(
                "Une zone touristique avec le nom '" + zoneTouristique.getNom() + "' existe déjà.");
        }

        // Résolution de la zone géographique parente (obligatoire)
        resolveAndSetZoneGeographique(zoneTouristique);

        // Validation de la contrainte polymorphique et résolution des relations
        validerEtResoudreLocalisationTerritoriale(zoneTouristique);

        return zoneTouristiqueRepository.save(zoneTouristique);
    }

    @Override
    @Transactional(readOnly = true)
    public ZoneTouristiqueEntity getZoneTouristiqueById(UUID id) {
        return zoneTouristiqueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Zone touristique non trouvée avec l'ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ZoneTouristiqueEntity> getZonesTouristiques(Pageable pageable) {
        return zoneTouristiqueRepository.findAll(pageable);
    }

    @Override
    public ZoneTouristiqueEntity updateZoneTouristique(UUID id, ZoneTouristiqueEntity zoneTouristiqueUpdate) {
        ZoneTouristiqueEntity existing = getZoneTouristiqueById(id);

        // Vérification unicité du code (en excluant l'entité courante)
        if (zoneTouristiqueUpdate.getCode() != null
            && !zoneTouristiqueUpdate.getCode().equals(existing.getCode())
            && zoneTouristiqueRepository.existsByCodeAndIdNot(zoneTouristiqueUpdate.getCode(), id)) {
            throw new IllegalArgumentException(
                "Une zone touristique avec le code '" + zoneTouristiqueUpdate.getCode() + "' existe déjà.");
        }
        // Vérification unicité du nom (en excluant l'entité courante)
        if (zoneTouristiqueUpdate.getNom() != null
            && !zoneTouristiqueUpdate.getNom().equals(existing.getNom())
            && zoneTouristiqueRepository.existsByNomAndIdNot(zoneTouristiqueUpdate.getNom(), id)) {
            throw new IllegalArgumentException(
                "Une zone touristique avec le nom '" + zoneTouristiqueUpdate.getNom() + "' existe déjà.");
        }

        // Application des champs modifiables (patch partiel)
        if (zoneTouristiqueUpdate.getCode() != null) existing.setCode(zoneTouristiqueUpdate.getCode());
        if (zoneTouristiqueUpdate.getNom() != null) existing.setNom(zoneTouristiqueUpdate.getNom());
        if (zoneTouristiqueUpdate.getDescription() != null) existing.setDescription(zoneTouristiqueUpdate.getDescription());
        if (zoneTouristiqueUpdate.getTypeZone() != null) existing.setTypeZone(zoneTouristiqueUpdate.getTypeZone());
        if (zoneTouristiqueUpdate.getSuperficieKm2() != null) existing.setSuperficieKm2(zoneTouristiqueUpdate.getSuperficieKm2());
        if (zoneTouristiqueUpdate.getLatitudeCentre() != null) existing.setLatitudeCentre(zoneTouristiqueUpdate.getLatitudeCentre());
        if (zoneTouristiqueUpdate.getLongitudeCentre() != null) existing.setLongitudeCentre(zoneTouristiqueUpdate.getLongitudeCentre());
        if (zoneTouristiqueUpdate.getActif() != null) existing.setActif(zoneTouristiqueUpdate.getActif());

        // Mise à jour de la zone géographique si un nouvel ID est fourni
        if (zoneTouristiqueUpdate.getZoneGeographique() != null
            && zoneTouristiqueUpdate.getZoneGeographique().getId() != null) {
            existing.setZoneGeographique(
                zoneGeographiqueRepository.findById(zoneTouristiqueUpdate.getZoneGeographique().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Zone géographique non trouvée avec l'ID: "
                        + zoneTouristiqueUpdate.getZoneGeographique().getId()))
            );
        }

        // Mise à jour de la localisation territoriale si fournie (contrainte polymorphique respectée)
        updateLocalisationTerritoriale(existing, zoneTouristiqueUpdate);

        return zoneTouristiqueRepository.save(existing);
    }

    @Override
    public void deleteZoneTouristique(UUID id) {
        if (!zoneTouristiqueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Zone touristique non trouvée avec l'ID: " + id);
        }
        zoneTouristiqueRepository.deleteById(id);
    }

    // ─────────────────────────── Méthodes privées ────────────────────────────

    /**
     * Résout et injecte la ZoneGeographiqueEntity parente.
     * La zone géographique est obligatoire pour toute zone touristique.
     */
    private void resolveAndSetZoneGeographique(ZoneTouristiqueEntity entity) {
        if (entity.getZoneGeographique() == null || entity.getZoneGeographique().getId() == null) {
            throw new IllegalArgumentException("La zone géographique parente est obligatoire.");
        }
        UUID zoneGeoId = entity.getZoneGeographique().getId();
        ZoneGeographiqueEntity zoneGeo = zoneGeographiqueRepository.findById(zoneGeoId)
            .orElseThrow(() -> new ResourceNotFoundException("Zone géographique non trouvée avec l'ID: " + zoneGeoId));
        entity.setZoneGeographique(zoneGeo);
    }

    private void validerEtResoudreLocalisationTerritoriale(ZoneTouristiqueEntity entity) {
        UUID departementId = entity.getDepartement() != null ? entity.getDepartement().getId() : null;
        UUID communeId = entity.getCommune() != null ? entity.getCommune().getId() : null;
        UUID villageId = entity.getVillage() != null ? entity.getVillage().getId() : null;

        // On prend le niveau le plus précis disponible et on ignore les autres
        final UUID finalVillageId = villageId;
        final UUID finalCommuneId = (villageId == null) ? communeId : null;
        final UUID finalDepartementId = (villageId == null && communeId == null) ? departementId : null;

        long nbLocalisations = countNonNull(finalDepartementId, finalCommuneId, finalVillageId);

        if (nbLocalisations > 1) {
            throw new IllegalArgumentException(
                "Une zone touristique doit être rattachée à exactement un niveau territorial "
                + "(département, commune OU village). Plusieurs ont été fournis.");
        }

        // Résolution de l'entité correspondante
        if (finalDepartementId != null) {
            entity.setDepartement(
                departementRepository.findById(finalDepartementId)
                    .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'ID: " + finalDepartementId)));
            entity.setCommune(null);
            entity.setVillage(null);
        } else if (finalCommuneId != null) {
            entity.setCommune(
                communeRepository.findById(finalCommuneId)
                    .orElseThrow(() -> new ResourceNotFoundException("Commune non trouvée avec l'ID: " + finalCommuneId)));
            entity.setDepartement(null);
            entity.setVillage(null);
        } else if (finalVillageId != null) {
            entity.setVillage(
                villageRepository.findById(finalVillageId)
                    .orElseThrow(() -> new ResourceNotFoundException("Village non trouvé avec l'ID: " + finalVillageId)));
            entity.setDepartement(null);
            entity.setCommune(null);
        }
        // Si aucun niveau n'est fourni, c'est permis (zone non encore localisée)
    }

    /**
     * Lors d'une mise à jour, applique la nouvelle localisation territoriale
     * en respectant la contrainte polymorphique (exactement 0 ou 1 niveau).
     */
    private void updateLocalisationTerritoriale(ZoneTouristiqueEntity existing, ZoneTouristiqueEntity update) {
        UUID departementId = update.getDepartement() != null ? update.getDepartement().getId() : null;
        UUID communeId = update.getCommune() != null ? update.getCommune().getId() : null;
        UUID villageId = update.getVillage() != null ? update.getVillage().getId() : null;

        long nbLocalisationsInitiaux = countNonNull(departementId, communeId, villageId);
        if (nbLocalisationsInitiaux == 0) return; // Pas de changement de localisation demandé

        // On prend le niveau le plus précis disponible et on ignore les autres
        final UUID finalVillageId = villageId;
        final UUID finalCommuneId = (villageId == null) ? communeId : null;
        final UUID finalDepartementId = (villageId == null && communeId == null) ? departementId : null;

        long nbLocalisations = countNonNull(finalDepartementId, finalCommuneId, finalVillageId);

        if (nbLocalisations > 1) {
            throw new IllegalArgumentException(
                "Une zone touristique doit être rattachée à exactement un niveau territorial "
                + "(département, commune OU village). Plusieurs ont été fournis.");
        }

        if (finalDepartementId != null) {
            existing.setDepartement(
                departementRepository.findById(finalDepartementId)
                    .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'ID: " + finalDepartementId)));
            existing.setCommune(null);
            existing.setVillage(null);
        } else if (finalCommuneId != null) {
            existing.setCommune(
                communeRepository.findById(finalCommuneId)
                    .orElseThrow(() -> new ResourceNotFoundException("Commune non trouvée avec l'ID: " + finalCommuneId)));
            existing.setDepartement(null);
            existing.setVillage(null);
        } else if (finalVillageId != null) {
            existing.setVillage(
                villageRepository.findById(finalVillageId)
                    .orElseThrow(() -> new ResourceNotFoundException("Village non trouvé avec l'ID: " + finalVillageId)));
            existing.setDepartement(null);
            existing.setCommune(null);
        }
    }

    private long countNonNull(Object... objects) {
        long count = 0;
        for (Object o : objects) if (o != null) count++;
        return count;
    }
}

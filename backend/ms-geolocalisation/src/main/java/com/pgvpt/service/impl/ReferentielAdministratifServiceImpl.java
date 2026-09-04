package com.pgvpt.service.impl;

import com.pgvpt.model.*;
import com.pgvpt.repository.*;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.service.ReferentielAdministratifService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReferentielAdministratifServiceImpl implements ReferentielAdministratifService {

    private final PaysRepository paysRepository;
    private final RegionRepository regionRepository;
    private final DepartementRepository departementRepository;
    private final CommuneRepository communeRepository;
    private final VillageRepository villageRepository;
    private final QuartierRepository quartierRepository;

    @Override
    public CommuneEntity createCommune(CommuneEntity commune) {
        if (commune.getCode() != null && communeRepository.existsByCode(commune.getCode())) {
            throw new IllegalArgumentException("Une commune avec le code " + commune.getCode() + " existe déjà.");
        }
        if (commune.getDepartement() == null || commune.getDepartement().getId() == null) {
            throw new ResourceNotFoundException("Le département parent est obligatoire.");
        }
        DepartementEntity departement = departementRepository.findById(commune.getDepartement().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Le département parent est obligatoire et doit exister."));
        commune.setDepartement(departement);
        return communeRepository.save(commune);
    }

    @Override
    public CommuneEntity getCommuneById(java.util.UUID id) {
        return communeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commune non trouvée avec l'ID: " + id));
    }

    @Override
    public CommuneEntity updateCommune(java.util.UUID id, CommuneEntity communeUpdate) {
        CommuneEntity existing = getCommuneById(id);
        if (communeUpdate.getCode() != null && !communeUpdate.getCode().equals(existing.getCode()) && communeRepository.existsByCode(communeUpdate.getCode())) {
            throw new IllegalArgumentException("Une commune avec le code " + communeUpdate.getCode() + " existe déjà.");
        }
        if (communeUpdate.getDepartement() != null && communeUpdate.getDepartement().getId() != null) {
            DepartementEntity departement = departementRepository.findById(communeUpdate.getDepartement().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Le département parent spécifié n'existe pas."));
            existing.setDepartement(departement);
        }
        if (communeUpdate.getCode() != null) existing.setCode(communeUpdate.getCode());
        if (communeUpdate.getNom() != null) existing.setNom(communeUpdate.getNom());
        if (communeUpdate.getDescription() != null) existing.setDescription(communeUpdate.getDescription());
        return communeRepository.save(existing);
    }

    @Override
    public void deleteCommune(java.util.UUID id) {
        if (!communeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Commune non trouvée avec l'ID: " + id);
        }
        communeRepository.deleteById(id);
    }

    @Override
    public DepartementEntity createDepartement(DepartementEntity departement) {
        if (departement.getCode() != null && departementRepository.existsByCode(departement.getCode())) {
            throw new IllegalArgumentException("Un département avec le code " + departement.getCode() + " existe déjà.");
        }
        if (departement.getRegion() == null || departement.getRegion().getId() == null) {
            throw new ResourceNotFoundException("La région parente est obligatoire.");
        }
        RegionEntity region = regionRepository.findById(departement.getRegion().getId())
                .orElseThrow(() -> new ResourceNotFoundException("La région parente est obligatoire et doit exister."));
        departement.setRegion(region);
        return departementRepository.save(departement);
    }

    @Override
    public DepartementEntity getDepartementById(java.util.UUID id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Département non trouvé avec l'ID: " + id));
    }

    @Override
    public DepartementEntity updateDepartement(java.util.UUID id, DepartementEntity departementUpdate) {
        DepartementEntity existing = getDepartementById(id);
        if (departementUpdate.getCode() != null && !departementUpdate.getCode().equals(existing.getCode()) && departementRepository.existsByCode(departementUpdate.getCode())) {
            throw new IllegalArgumentException("Un département avec le code " + departementUpdate.getCode() + " existe déjà.");
        }
        if (departementUpdate.getRegion() != null && departementUpdate.getRegion().getId() != null) {
            RegionEntity region = regionRepository.findById(departementUpdate.getRegion().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("La région parente spécifiée n'existe pas."));
            existing.setRegion(region);
        }
        if (departementUpdate.getCode() != null) existing.setCode(departementUpdate.getCode());
        if (departementUpdate.getNom() != null) existing.setNom(departementUpdate.getNom());
        if (departementUpdate.getDescription() != null) existing.setDescription(departementUpdate.getDescription());
        return departementRepository.save(existing);
    }

    @Override
    public void deleteDepartement(java.util.UUID id) {
        if (!departementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Département non trouvé avec l'ID: " + id);
        }
        departementRepository.deleteById(id);
    }

    @Override
    public PaysEntity createPays(PaysEntity pays) {
        if (pays.getCode() != null && paysRepository.existsByCode(pays.getCode())) {
            throw new IllegalArgumentException("Un pays avec le code " + pays.getCode() + " existe déjà.");
        }
        return paysRepository.save(pays);
    }

    @Override
    public PaysEntity getPaysById(java.util.UUID id) {
        return paysRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pays non trouvé avec l'ID: " + id));
    }

    @Override
    public PaysEntity updatePays(java.util.UUID id, PaysEntity paysUpdate) {
        PaysEntity existing = getPaysById(id);
        if (paysUpdate.getCode() != null && !paysUpdate.getCode().equals(existing.getCode()) && paysRepository.existsByCode(paysUpdate.getCode())) {
            throw new IllegalArgumentException("Un pays avec le code " + paysUpdate.getCode() + " existe déjà.");
        }
        if (paysUpdate.getCode() != null) existing.setCode(paysUpdate.getCode());
        if (paysUpdate.getNom() != null) existing.setNom(paysUpdate.getNom());
        if (paysUpdate.getCodeIso2() != null) existing.setCodeIso2(paysUpdate.getCodeIso2());
        if (paysUpdate.getCodeIso3() != null) existing.setCodeIso3(paysUpdate.getCodeIso3());
        if (paysUpdate.getDevise() != null) existing.setDevise(paysUpdate.getDevise());
        if (paysUpdate.getCodeDevise() != null) existing.setCodeDevise(paysUpdate.getCodeDevise());
        if (paysUpdate.getIndicatifTelephonique() != null) existing.setIndicatifTelephonique(paysUpdate.getIndicatifTelephonique());
        if (paysUpdate.getLangueOfficielle() != null) existing.setLangueOfficielle(paysUpdate.getLangueOfficielle());
        if (paysUpdate.getContinent() != null) existing.setContinent(paysUpdate.getContinent());
        if (paysUpdate.getActif() != null) existing.setActif(paysUpdate.getActif());
        return paysRepository.save(existing);
    }

    @Override
    public void deletePays(java.util.UUID id) {
        if (!paysRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pays non trouvé avec l'ID: " + id);
        }
        paysRepository.deleteById(id);
    }

    @Override
    public QuartierEntity createQuartier(QuartierEntity quartier) {
        if (quartier.getCode() != null && quartierRepository.existsByCode(quartier.getCode())) {
            throw new IllegalArgumentException("Un quartier avec le code " + quartier.getCode() + " existe déjà.");
        }
        if (quartier.getCommune() != null && quartier.getCommune().getId() != null) {
            CommuneEntity commune = communeRepository.findById(quartier.getCommune().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("La commune parente est introuvable."));
            quartier.setCommune(commune);
        }
        if (quartier.getVillage() != null && quartier.getVillage().getId() != null) {
            VillageEntity village = villageRepository.findById(quartier.getVillage().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Le village parent est introuvable."));
            quartier.setVillage(village);
        }
        return quartierRepository.save(quartier);
    }

    @Override
    public QuartierEntity getQuartierById(java.util.UUID id) {
        return quartierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quartier non trouvé avec l'ID: " + id));
    }

    @Override
    public QuartierEntity updateQuartier(java.util.UUID id, QuartierEntity quartierUpdate) {
        QuartierEntity existing = getQuartierById(id);
        if (quartierUpdate.getCode() != null && !quartierUpdate.getCode().equals(existing.getCode()) && quartierRepository.existsByCode(quartierUpdate.getCode())) {
            throw new IllegalArgumentException("Un quartier avec le code " + quartierUpdate.getCode() + " existe déjà.");
        }
        if (quartierUpdate.getCommune() != null && quartierUpdate.getCommune().getId() != null) {
            CommuneEntity commune = communeRepository.findById(quartierUpdate.getCommune().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Commune non trouvée."));
            existing.setCommune(commune);
        }
        if (quartierUpdate.getVillage() != null && quartierUpdate.getVillage().getId() != null) {
            VillageEntity village = villageRepository.findById(quartierUpdate.getVillage().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Village non trouvé."));
            existing.setVillage(village);
        }
        if (quartierUpdate.getCode() != null) existing.setCode(quartierUpdate.getCode());
        if (quartierUpdate.getNom() != null) existing.setNom(quartierUpdate.getNom());
        if (quartierUpdate.getDescription() != null) existing.setDescription(quartierUpdate.getDescription());
        return quartierRepository.save(existing);
    }

    @Override
    public void deleteQuartier(java.util.UUID id) {
        if (!quartierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quartier non trouvé avec l'ID: " + id);
        }
        quartierRepository.deleteById(id);
    }

    @Override
    public RegionEntity createRegion(RegionEntity region) {
        if (region.getCode() != null && regionRepository.existsByCode(region.getCode())) {
            throw new IllegalArgumentException("Une région avec le code " + region.getCode() + " existe déjà.");
        }
        if (region.getPaysEntity() == null || region.getPaysEntity().getId() == null) {
            throw new ResourceNotFoundException("Le pays parent est obligatoire.");
        }
        PaysEntity pays = paysRepository.findById(region.getPaysEntity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Le pays parent est obligatoire et doit exister."));
        region.setPaysEntity(pays);
        return regionRepository.save(region);
    }

    @Override
    public RegionEntity getRegionById(java.util.UUID id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Région non trouvée avec l'ID: " + id));
    }

    @Override
    public RegionEntity updateRegion(java.util.UUID id, RegionEntity regionUpdate) {
        RegionEntity existing = getRegionById(id);
        if (regionUpdate.getCode() != null && !regionUpdate.getCode().equals(existing.getCode()) && regionRepository.existsByCode(regionUpdate.getCode())) {
            throw new IllegalArgumentException("Une région avec le code " + regionUpdate.getCode() + " existe déjà.");
        }
        if (regionUpdate.getPaysEntity() != null && regionUpdate.getPaysEntity().getId() != null) {
            PaysEntity pays = paysRepository.findById(regionUpdate.getPaysEntity().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pays parent non trouvé."));
            existing.setPaysEntity(pays);
        }
        if (regionUpdate.getCode() != null) existing.setCode(regionUpdate.getCode());
        if (regionUpdate.getNom() != null) existing.setNom(regionUpdate.getNom());
        if (regionUpdate.getCodePostal() != null) existing.setCodePostal(regionUpdate.getCodePostal());
        if (regionUpdate.getDescription() != null) existing.setDescription(regionUpdate.getDescription());
        return regionRepository.save(existing);
    }

    @Override
    public void deleteRegion(java.util.UUID id) {
        if (!regionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Région non trouvée avec l'ID: " + id);
        }
        regionRepository.deleteById(id);
    }

    @Override
    public VillageEntity createVillage(VillageEntity village) {
        if (village.getCode() != null && villageRepository.existsByCode(village.getCode())) {
            throw new IllegalArgumentException("Un village avec le code " + village.getCode() + " existe déjà.");
        }
        if (village.getCommune() != null && village.getCommune().getId() != null) {
            CommuneEntity commune = communeRepository.findById(village.getCommune().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("La commune parente est introuvable."));
            village.setCommune(commune);
        }
        return villageRepository.save(village);
    }

    @Override
    public VillageEntity getVillageById(java.util.UUID id) {
        return villageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Village non trouvé avec l'ID: " + id));
    }

    @Override
    public VillageEntity updateVillage(java.util.UUID id, VillageEntity villageUpdate) {
        VillageEntity existing = getVillageById(id);
        if (villageUpdate.getCode() != null && !villageUpdate.getCode().equals(existing.getCode()) && villageRepository.existsByCode(villageUpdate.getCode())) {
            throw new IllegalArgumentException("Un village avec le code " + villageUpdate.getCode() + " existe déjà.");
        }
        if (villageUpdate.getCommune() != null && villageUpdate.getCommune().getId() != null) {
            CommuneEntity commune = communeRepository.findById(villageUpdate.getCommune().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Commune parente non trouvée."));
            existing.setCommune(commune);
        }
        if (villageUpdate.getCode() != null) existing.setCode(villageUpdate.getCode());
        if (villageUpdate.getNom() != null) existing.setNom(villageUpdate.getNom());
        if (villageUpdate.getDescription() != null) existing.setDescription(villageUpdate.getDescription());
        return villageRepository.save(existing);
    }

    @Override
    public void deleteVillage(java.util.UUID id) {
        if (!villageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Village non trouvé avec l'ID: " + id);
        }
        villageRepository.deleteById(id);
    }

    @Override
    public Page<CommuneEntity> getCommunes(Pageable pageable) {
        return communeRepository.findAll(pageable);
    }

    @Override
    public Page<DepartementEntity> getDepartements(Pageable pageable) {
        return departementRepository.findAll(pageable);
    }

    @Override
    public Page<PaysEntity> getPays(Pageable pageable) {
        return paysRepository.findAll(pageable);
    }

    @Override
    public Page<QuartierEntity> getQuartiers(Pageable pageable) {
        return quartierRepository.findAll(pageable);
    }

    @Override
    public Page<RegionEntity> getRegions(Pageable pageable) {
        return regionRepository.findAll(pageable);
    }

    @Override
    public Page<VillageEntity> getVillages(Pageable pageable) {
        return villageRepository.findAll(pageable);
    }
}

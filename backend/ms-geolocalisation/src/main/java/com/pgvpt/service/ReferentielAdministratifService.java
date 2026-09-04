package com.pgvpt.service;

import com.pgvpt.model.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReferentielAdministratifService {

    CommuneEntity createCommune(@Valid @RequestBody CommuneEntity commune);
    CommuneEntity getCommuneById(java.util.UUID id);
    CommuneEntity updateCommune(java.util.UUID id, CommuneEntity commune);
    void deleteCommune(java.util.UUID id);

    DepartementEntity createDepartement(@Valid @RequestBody DepartementEntity departement);
    DepartementEntity getDepartementById(java.util.UUID id);
    DepartementEntity updateDepartement(java.util.UUID id, DepartementEntity departement);
    void deleteDepartement(java.util.UUID id);

    PaysEntity createPays(PaysEntity pays);
    PaysEntity getPaysById(java.util.UUID id);
    PaysEntity updatePays(java.util.UUID id, PaysEntity pays);
    void deletePays(java.util.UUID id);

    QuartierEntity createQuartier(@Valid @RequestBody QuartierEntity quartier);
    QuartierEntity getQuartierById(java.util.UUID id);
    QuartierEntity updateQuartier(java.util.UUID id, QuartierEntity quartier);
    void deleteQuartier(java.util.UUID id);

    RegionEntity createRegion(@Valid @RequestBody RegionEntity region);
    RegionEntity getRegionById(java.util.UUID id);
    RegionEntity updateRegion(java.util.UUID id, RegionEntity region);
    void deleteRegion(java.util.UUID id);

    VillageEntity createVillage(@Valid @RequestBody VillageEntity village);
    VillageEntity getVillageById(java.util.UUID id);
    VillageEntity updateVillage(java.util.UUID id, VillageEntity village);
    void deleteVillage(java.util.UUID id);

    Page<CommuneEntity> getCommunes(Pageable pageable);

    Page<DepartementEntity> getDepartements(Pageable pageable);

    Page<PaysEntity> getPays(Pageable pageable);

    Page<QuartierEntity> getQuartiers(Pageable pageable);

    Page<RegionEntity> getRegions(Pageable pageable);

    Page<VillageEntity> getVillages(Pageable pageable);

}

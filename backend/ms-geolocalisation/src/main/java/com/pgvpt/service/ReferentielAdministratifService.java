package com.pgvpt.service;

import com.pgvpt.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReferentielAdministratifService {

    PagePays getAllPays(Pageable pageable);
    Pays createPays(PaysCreate dto);
    Pays getPaysById(UUID id);
    Pays updatePays(UUID id, PaysUpdate dto);
    void deletePays(UUID id);

    PageRegion getAllRegions(Pageable pageable);
    Region createRegion(RegionCreate dto);
    Region getRegionById(UUID id);
    Region updateRegion(UUID id, RegionUpdate region);
    void deleteRegion(UUID id);

    PageDepartement getAllDepartements(Pageable pageable);
    Departement createDepartement(DepartementCreate dto);
    Departement getDepartementById(UUID id);
    Departement updateDepartement(UUID id, DepartementUpdate dto);
    void deleteDepartement(UUID id);

    PageCommune getAllCommunes(Pageable pageable);
    Commune createCommune(CommuneCreate dto);
    Commune getCommuneById(UUID id);
    Commune updateCommune(UUID id, CommuneUpdate dto);
    void deleteCommune(UUID id);

    PageVillage getVillages(Pageable pageable);
    Village createVillage(VillageCreate dto);
    Village getVillageById(UUID id);
    Village updateVillage(UUID id, VillageUpdate dto);
    void deleteVillage(UUID id);

    PageQuartier getQuartiers(Pageable pageable);
    Quartier createQuartier(QuartierCreate dto);
    Quartier getQuartierById(UUID id);
    Quartier updateQuartier(UUID id, QuartierUpdate dto);
    void deleteQuartier(UUID id);
}

package com.pgvpt.controller;

import com.pgvpt.api.*;
import com.pgvpt.dto.*;
import com.pgvpt.service.ReferentielAdministratifService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReferentielAdministratifController implements ReferentielAdministratifApi {

    private final ReferentielAdministratifService service;


    @Override
    public ResponseEntity<Commune> createCommune(CommuneCreate communeCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCommune(communeCreate));
    }

    @Override
    public ResponseEntity<Departement> createDepartement(DepartementCreate departementCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDepartement(departementCreate));
    }

    @Override
    public ResponseEntity<Pays> createPays(PaysCreate paysCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPays(paysCreate));
    }

    @Override
    public ResponseEntity<Quartier> createQuartier(QuartierCreate quartierCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createQuartier(quartierCreate));
    }

    @Override
    public ResponseEntity<Region> createRegion(RegionCreate regionCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRegion(regionCreate));
    }

    @Override
    public ResponseEntity<Village> createVillage(VillageCreate villageCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createVillage(villageCreate));
    }

    @Override
    public ResponseEntity<Void> deleteCommune(UUID id) {
        service.deleteCommune(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteDepartement(UUID id) {
        service.deleteDepartement(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletePays(UUID id) {
        service.deletePays(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteQuartier(UUID id) {
        service.deleteQuartier(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteRegion(UUID id) {
        service.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteVillage(UUID id) {
        service.deleteVillage(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PagePays> getAllPays(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllPays(pageable));
    }

    @Override
    public ResponseEntity<Commune> getCommune(UUID id) {
        return ResponseEntity.ok(service.getCommuneById(id));
    }

    @Override
    public ResponseEntity<PageCommune> getCommunes(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllCommunes(pageable));
    }

    @Override
    public ResponseEntity<Departement> getDepartement(UUID id) {
        return ResponseEntity.ok(service.getDepartementById(id));
    }

    @Override
    public ResponseEntity<PageDepartement> getDepartements(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllDepartements(pageable));
    }

    @Override
    public ResponseEntity<Pays> getPays(UUID id) {
        return ResponseEntity.ok(service.getPaysById(id));
    }

    @Override
    public ResponseEntity<Quartier> getQuartier(UUID id) {
        return ResponseEntity.ok(service.getQuartierById(id));
    }

    @Override
    public ResponseEntity<PageQuartier> getQuartiers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getQuartiers(pageable));
    }

    @Override
    public ResponseEntity<Region> getRegion(UUID id) {
        return ResponseEntity.ok(service.getRegionById(id));
    }

    @Override
    public ResponseEntity<PageRegion> getRegions(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAllRegions(pageable));
    }

    @Override
    public ResponseEntity<Village> getVillage(UUID id) {
        return ResponseEntity.ok(service.getVillageById(id));
    }

    @Override
    public ResponseEntity<PageVillage> getVillages(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getVillages(pageable));
    }

    @Override
    public ResponseEntity<Commune> updateCommune(UUID id, CommuneUpdate communeUpdate) {
        return ResponseEntity.ok(service.updateCommune(id, communeUpdate));
    }

    @Override
    public ResponseEntity<Departement> updateDepartement(UUID id, DepartementUpdate departementUpdate) {
        return ResponseEntity.ok(service.updateDepartement(id, departementUpdate));
    }

    @Override
    public ResponseEntity<Pays> updatePays(UUID id, PaysUpdate paysUpdate) {
        return ResponseEntity.ok(service.updatePays(id, paysUpdate));
    }

    @Override
    public ResponseEntity<Quartier> updateQuartier(UUID id, QuartierUpdate quartierUpdate) {
        return ResponseEntity.ok(service.updateQuartier(id, quartierUpdate));
    }

    @Override
    public ResponseEntity<Region> updateRegion(UUID id, RegionUpdate regionUpdate) {
        return ResponseEntity.ok(service.updateRegion(id, regionUpdate));
    }

    @Override
    public ResponseEntity<Village> updateVillage(UUID id, VillageUpdate villageUpdate) {
        return ResponseEntity.ok(service.updateVillage(id, villageUpdate));
    }
}

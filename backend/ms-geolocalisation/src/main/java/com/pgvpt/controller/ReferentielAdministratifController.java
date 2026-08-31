package com.pgvpt.controller;

import com.dev.pgvpt.api.ReferentielAdministratifApi;
import com.dev.pgvpt.api.model.*;
import com.pgvpt.mapper.ReferentielAdministratifApiMapper;
import com.pgvpt.model.*;
import com.pgvpt.service.ReferentielAdministratifService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReferentielAdministratifController implements ReferentielAdministratifApi {

    private final ReferentielAdministratifService referentielAdministratifService;

    private final ReferentielAdministratifApiMapper mapper;

    @Override
    public ResponseEntity<Commune> createCommune(CommuneCreate communeCreate) {

        var entity = mapper.toEntity(communeCreate);
        var created = referentielAdministratifService.createCommune(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Departement> createDepartement(DepartementCreate departementCreate) {

        var entity = mapper.toEntity(departementCreate);
        var created = referentielAdministratifService.createDepartement(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Pays> createPays(PaysCreate paysCreate) {

        var entity = mapper.toEntity(paysCreate);
        var created = referentielAdministratifService.createPays(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Quartier> createQuartier(QuartierCreate quartierCreate) {

        var entity = mapper.toEntity(quartierCreate);
        var created = referentielAdministratifService.createQuartier(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Region> createRegion(RegionCreate regionCreate) {

        var entity = mapper.toEntity(regionCreate);
        var created = referentielAdministratifService.createRegion(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Village> createVillage(VillageCreate villageCreate) {

        var entity = mapper.toEntity(villageCreate);
        var created = referentielAdministratifService.createVillage(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toApi(created));
    }

    @Override
    public ResponseEntity<Void> deleteCommune(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteDepartement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletePays(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteQuartier(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRegion(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteVillage(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<PagePays> getAllPays(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<Commune> getCommune(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<PageCommune> getCommunes(Integer page, Integer size) {

        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<CommuneEntity> pageEntity = referentielAdministratifService.getCommunes(pageable);
        List<Commune> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body((PageCommune) dtos);
    }

    @Override
    public ResponseEntity<Departement> getDepartement(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<PageDepartement> getDepartements(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<Pays> getPays(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<Quartier> getQuartier(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Departement>> getDpartements(Integer page, Integer size) {

        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<DepartementEntity> pageEntity = referentielAdministratifService.getDpartements(pageable);
        List<Departement> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body(dtos);
    }

    @Override
    public ResponseEntity<List<Pays>> getPays(Integer page, Integer size) {

        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<PaysEntity> pageEntity = referentielAdministratifService.getPays(pageable);
        List<Pays> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body(dtos);
    }

    @Override
    public ResponseEntity<PageQuartier> getQuartiers(Integer page, Integer size) {

        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<QuartierEntity> pageEntity = referentielAdministratifService.getQuartiers(pageable);
        List<Quartier> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body((PageQuartier) dtos);
    }

    @Override
    public ResponseEntity<Region> getRegion(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<PageRegion> getRegions(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<Village> getVillage(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Region>> getRgions(Integer page, Integer size) {

        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<RegionEntity> pageEntity = referentielAdministratifService.getRgions(pageable);
        List<Region> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body(dtos);
    }

    @Override
    public ResponseEntity<PageVillage> getVillages(Integer page, Integer size) {

        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 20;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("nom").ascending());

        Page<VillageEntity> pageEntity = referentielAdministratifService.getVillages(pageable);
        List<Village> dtos = pageEntity.getContent().stream().map(mapper::toApi).toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(pageEntity.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pageEntity.getTotalPages()));

        return ResponseEntity.ok().headers(headers).body((PageVillage) dtos);
    }

    @Override
    public ResponseEntity<Commune> updateCommune(UUID id, CommuneUpdate communeUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Departement> updateDepartement(UUID id, DepartementUpdate departementUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Pays> updatePays(UUID id, PaysUpdate paysUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Quartier> updateQuartier(UUID id, QuartierUpdate quartierUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Region> updateRegion(UUID id, RegionUpdate regionUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Village> updateVillage(UUID id, VillageUpdate villageUpdate) {
        return null;
    }
}

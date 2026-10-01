package com.pgvpt.service.impl;

import com.pgvpt.dto.*;
import com.pgvpt.exception.ResourceNotFoundException;
import com.pgvpt.mapper.ReferentielAdministratifMapper;
import com.pgvpt.model.*;
import com.pgvpt.repository.*;
import com.pgvpt.service.ReferentielAdministratifService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

    private final ReferentielAdministratifMapper mapper;


    @Override
    public PagePays getAllPays(Pageable pageable) {
        Page<PaysEntity> page = paysRepository.findAll(pageable);
        return new PagePays()
                .content(page.getContent().stream().map(mapper::toDtoPays).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public Pays createPays(PaysCreate dto) {
        PaysEntity entity = mapper.toEntityPays(dto);
        return mapper.toDtoPays(paysRepository.save(entity));
    }

    @Override
    public Pays getPaysById(UUID id) {
        return paysRepository.findById(id).map(mapper::toDtoPays)
                .orElseThrow(() -> new ResourceNotFoundException("Pays introuvable"));
    }

    @Override
    @Transactional
    public Pays updatePays(UUID id, PaysUpdate dto) {

        PaysEntity entity = paysRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pays introuvable"));

        mapper.updateEntityPays(dto, entity);
        return mapper.toDtoPays(paysRepository.save(entity));
    }

    @Override
    public void deletePays(UUID id) {
        if (!paysRepository.existsById(id))
            throw new ResourceNotFoundException("Pays introuvable");
        paysRepository.deleteById(id);
    }

    @Override
    public PageRegion getAllRegions(Pageable pageable) {
        Page<RegionEntity> page = regionRepository.findAll(pageable);
        return new PageRegion()
                .content(page.getContent().stream().map(mapper::toDtoRegion).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    @Transactional
    public Region createRegion(RegionCreate dto) {
        try {
            RegionEntity entity = mapper.toEntityRegion(dto);
            return mapper.toDtoRegion(regionRepository.save(entity));
        } catch (Exception e) {
            e.printStackTrace(); // Affichera la cause exacte (le "Caused by") dans la console
            throw e;
        }
    }

    @Override
    public Region getRegionById(UUID id) {
        return regionRepository.findById(id).map(mapper::toDtoRegion)
                .orElseThrow(() -> new ResourceNotFoundException("Region introuvable"));
    }

    @Override
    @Transactional
    public Region updateRegion(UUID id, RegionUpdate dto) {

        RegionEntity entity = regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region introuvable"));

        mapper.updateEntityRegion(dto, entity);
        return mapper.toDtoRegion(regionRepository.save(entity));
    }

    @Override
    public void deleteRegion(UUID id) {
        if (!regionRepository.existsById(id))
            throw new ResourceNotFoundException("Region introuvable");
        regionRepository.deleteById(id);
    }

    @Override
    public PageDepartement getAllDepartements(Pageable pageable) {
        Page<DepartementEntity> page = departementRepository.findAll(pageable);
        return new PageDepartement()
                .content(page.getContent().stream().map(mapper::toDtoDept).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    @Transactional
    public Departement createDepartement(DepartementCreate dto) {
        DepartementEntity entity = mapper.toEntityDept(dto);
        return mapper.toDtoDept(departementRepository.save(entity));
    }

    @Override
    public Departement getDepartementById(UUID id) {
        return departementRepository.findById(id).map(mapper::toDtoDept)
                .orElseThrow(() -> new ResourceNotFoundException("Departement introuvable"));
    }

    @Override
    @Transactional
    public Departement updateDepartement(UUID id, DepartementUpdate dto) {
        DepartementEntity entity = departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departement introuvable"));

        mapper.updateEntityDept(dto, entity);
        return mapper.toDtoDept(departementRepository.save(entity));
    }

    @Override
    public void deleteDepartement(UUID id) {
        if (!departementRepository.existsById(id))
            throw new ResourceNotFoundException("Departement introuvable");
        departementRepository.deleteById(id);
    }

    @Override
    public PageCommune getAllCommunes(Pageable pageable) {
        Page<CommuneEntity> page = communeRepository.findAll(pageable);
        return new PageCommune()
                .content(page.getContent().stream().map(mapper::toDtoCom).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public Commune createCommune(CommuneCreate dto) {
        CommuneEntity entity = mapper.toEntityCom(dto);
        return mapper.toDtoCom(communeRepository.save(entity));
    }

    @Override
    public Commune getCommuneById(UUID id) {
        return communeRepository.findById(id).map(mapper::toDtoCom)
                .orElseThrow(() -> new ResourceNotFoundException("Commune introuvable"));
    }

    @Override
    public Commune updateCommune(UUID id, CommuneUpdate dto) {
        CommuneEntity entity = communeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commune introuvable"));

        mapper.updateEntityCom(dto, entity);
        return mapper.toDtoCom(communeRepository.save(entity));
    }

    @Override
    public void deleteCommune(UUID id) {
        if (!communeRepository.existsById(id))
            throw new ResourceNotFoundException("Commune introuvable");
        communeRepository.deleteById(id);
    }

    @Override
    public PageVillage getVillages(Pageable pageable) {
        Page<VillageEntity> page = villageRepository.findAll(pageable);
        return new PageVillage()
                .content(page.getContent().stream().map(mapper::toDtoVil).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public Village createVillage(VillageCreate dto) {
        VillageEntity entity = mapper.toEntityVil(dto);
        return mapper.toDtoVil(villageRepository.save(entity));
    }

    @Override
    public Village getVillageById(UUID id) {
        return villageRepository.findById(id).map(mapper::toDtoVil)
                .orElseThrow(() -> new ResourceNotFoundException("Village introuvable"));
    }

    @Override
    public Village updateVillage(UUID id, VillageUpdate dto) {
        VillageEntity entity = villageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Village introuvable"));

        mapper.updateEntityVil(dto, entity);
        return mapper.toDtoVil(villageRepository.save(entity));
    }

    @Override
    public void deleteVillage(UUID id) {
        if (!villageRepository.existsById(id)) throw new ResourceNotFoundException("Village introuvable");
        villageRepository.deleteById(id);
    }

    @Override
    public PageQuartier getQuartiers(Pageable pageable) {
        Page<QuartierEntity> page = quartierRepository.findAll(pageable);
        return new PageQuartier()
                .content(page.getContent().stream().map(mapper::toDtoQuart).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages())
                .first(page.isFirst()).last(page.isLast());
    }

    @Override
    public Quartier createQuartier(QuartierCreate dto) {
        QuartierEntity entity = mapper.toEntityQuart(dto);
        return mapper.toDtoQuart(quartierRepository.save(entity));
    }

    @Override
    public Quartier getQuartierById(UUID id) {
        return quartierRepository.findById(id).map(mapper::toDtoQuart)
                .orElseThrow(() -> new ResourceNotFoundException("Quartier introuvable"));
    }

    @Override
    public Quartier updateQuartier(UUID id, QuartierUpdate dto) {
        QuartierEntity entity = quartierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quartier introuvable"));

        mapper.updateEntityQuart(dto, entity);
        return mapper.toDtoQuart(quartierRepository.save(entity));
    }

    @Override
    public void deleteQuartier(UUID id) {
        if (!quartierRepository.existsById(id)) throw new ResourceNotFoundException("Quartier introuvable");
        quartierRepository.deleteById(id);
    }
}

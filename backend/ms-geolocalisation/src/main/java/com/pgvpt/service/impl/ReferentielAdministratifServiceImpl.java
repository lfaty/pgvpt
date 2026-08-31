package com.pgvpt.service.impl;

import com.pgvpt.model.*;
import com.pgvpt.repository.*;
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

        return communeRepository.save(commune);
    }

    @Override
    public DepartementEntity createDepartement(DepartementEntity departement) {

        return departementRepository.save(departement);
    }

    @Override
    public PaysEntity createPays(PaysEntity pays) {

        return paysRepository.save(pays);
    }

    @Override
    public QuartierEntity createQuartier(QuartierEntity quartier) {
        return quartierRepository.save(quartier);
    }

    @Override
    public RegionEntity createRegion(RegionEntity region) {
        return regionRepository.save(region);
    }

    @Override
    public VillageEntity createVillage(VillageEntity village) {
        return villageRepository.save(village);
    }

    @Override
    public Page<CommuneEntity> getCommunes(Pageable pageable) {

        return communeRepository.findAll(pageable);
    }

    @Override
    public Page<DepartementEntity> getDpartements(Pageable pageable) {
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
    public Page<RegionEntity> getRgions(Pageable pageable) {
        return regionRepository.findAll(pageable);
    }

    @Override
    public Page<VillageEntity> getVillages(Pageable pageable) {
        return villageRepository.findAll(pageable);
    }
}

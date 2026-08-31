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

    DepartementEntity createDepartement(@Valid @RequestBody DepartementEntity departement);

    PaysEntity createPays(PaysEntity pays);

    QuartierEntity createQuartier(@Valid @RequestBody QuartierEntity quartier);

    RegionEntity createRegion(@Valid @RequestBody RegionEntity region);

    VillageEntity createVillage(@Valid @RequestBody VillageEntity village);

    Page<CommuneEntity> getCommunes(Pageable pageable);

    Page<DepartementEntity> getDpartements(Pageable pageable);

    Page<PaysEntity> getPays(Pageable pageable);

    Page<QuartierEntity> getQuartiers(Pageable pageable);

    Page<RegionEntity> getRgions(Pageable pageable);

    Page<VillageEntity> getVillages(Pageable pageable);

}

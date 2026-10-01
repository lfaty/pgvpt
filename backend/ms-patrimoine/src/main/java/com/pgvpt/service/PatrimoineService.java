package com.pgvpt.service;

import com.pgvpt.dto.*;
import com.pgvpt.record.PatrimoineSearchCriteria;

import java.util.List;
import java.util.UUID;

public interface PatrimoineService {
    PagePatrimoine getAllPatrimoines(int page, int size);
    PagePatrimoine getPatrimoines(int page, int size, String sort, PatrimoineSearchCriteria criteria);
    Patrimoine getById(UUID id);
    void delete(UUID id);
    Patrimoine createPatrimoine(PatrimoineCreate dot);
    Patrimoine updatePatrimoine(UUID id, PatrimoineUpdate dot);
    Patrimoine updateStatutPatrimoine(UUID id, PatrimoineStatutUpdate dto);
    Patrimoine depublier(UUID id);

    List<HoraireOuverture> getHoraires(UUID id);
    List<HoraireOuverture> updateHoraires(UUID id, List<HoraireOuverture> dtos);
    Conservation getConservation(UUID id);
    Conservation updateConservation(UUID id, Conservation dto);
}

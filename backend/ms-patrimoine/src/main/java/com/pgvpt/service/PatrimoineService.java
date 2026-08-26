package com.pgvpt.service;

import com.pgvpt.dto.Patrimoine;
import com.pgvpt.dto.PatrimoineCreate;
import com.pgvpt.dto.PatrimoineUpdate;
import com.pgvpt.dto.PagePatrimoine;
import java.util.UUID;

public interface PatrimoineService {

    Patrimoine create(PatrimoineCreate patrimoineCreate);

    Patrimoine getPatrimoine(UUID id);

    void deletePatrimoine(UUID id);

    Patrimoine updatePatrimoine(UUID id, PatrimoineUpdate patrimoineUpdate);

    PagePatrimoine getPatrimoines(Integer page, Integer size);
}

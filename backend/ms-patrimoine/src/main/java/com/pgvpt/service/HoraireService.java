package com.pgvpt.service;

import com.pgvpt.dto.HoraireOuverture;
import java.util.List;
import java.util.UUID;

public interface HoraireService {
    List<HoraireOuverture> getHoraires(UUID id);
    List<HoraireOuverture> updateHoraires(UUID id, List<HoraireOuverture> horaires);
}

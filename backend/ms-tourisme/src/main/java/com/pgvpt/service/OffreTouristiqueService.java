package com.pgvpt.service;

import com.pgvpt.model.OffreTouristiqueEntity;
import org.springframework.data.domain.Page;
import com.pgvpt.enums.*;

import java.math.BigDecimal;
import java.util.UUID;

public interface OffreTouristiqueService {

    OffreTouristiqueEntity getById(UUID id);

    Page<OffreTouristiqueEntity> search(int page, int size, String search, UUID zoneTouristiqueId, UUID acteurId, UUID circuitId, CategorieOffre categorie, StatutOffre statut);

    OffreTouristiqueEntity create(OffreTouristiqueEntity request);

    OffreTouristiqueEntity update(UUID id, OffreTouristiqueEntity request);

    void delete(UUID id);

    OffreTouristiqueEntity publier(UUID id);

    OffreTouristiqueEntity depublier(UUID id);

    OffreTouristiqueEntity activer(UUID id);

    OffreTouristiqueEntity desactiver(UUID id);

    OffreTouristiqueEntity archiver(UUID id);

    BigDecimal calculerPrix(UUID id);
}

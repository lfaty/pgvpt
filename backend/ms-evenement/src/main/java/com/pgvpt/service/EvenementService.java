package com.pgvpt.service;

import com.pgvpt.enums.StatutEvenement;
import com.pgvpt.enums.TypeEvenement;
import com.pgvpt.model.EvenementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface EvenementService {

    EvenementEntity create(EvenementEntity request);

    EvenementEntity getById(UUID id);

    Page<EvenementEntity> search(UUID zoneTouristiqueId, TypeEvenement type, StatutEvenement statut, Instant dateDebut, Instant dateFin, String search, Pageable pageable);

    EvenementEntity update(UUID id, EvenementEntity request);

    void delete(UUID id);

    EvenementEntity programmer(UUID id);

    EvenementEntity ouvrir(UUID id);

    EvenementEntity annuler(UUID id);

    EvenementEntity terminer(UUID id);
}
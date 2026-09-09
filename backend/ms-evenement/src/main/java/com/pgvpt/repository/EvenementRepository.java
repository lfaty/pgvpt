package com.pgvpt.repository;

import com.pgvpt.enums.StatutEvenement;
import com.pgvpt.enums.TypeEvenement;
import com.pgvpt.model.EvenementEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EvenementRepository extends JpaRepository<EvenementEntity, UUID> {

    Page<EvenementEntity> findByZoneTouristiqueId(UUID zoneTouristiqueId, Pageable pageable);

    Page<EvenementEntity> findByType(TypeEvenement type, Pageable pageable);

    Page<EvenementEntity> findByStatut(StatutEvenement statut, Pageable pageable);

    Page<EvenementEntity>
    findByZoneTouristiqueIdAndType(UUID zoneTouristiqueId, TypeEvenement type, Pageable pageable);

    Page<EvenementEntity>
    findByTitreContainingIgnoreCase(String titre, Pageable pageable);

    boolean existsByTitreIgnoreCase(String titre);
}

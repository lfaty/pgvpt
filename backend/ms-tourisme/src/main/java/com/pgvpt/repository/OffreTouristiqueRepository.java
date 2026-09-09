package com.pgvpt.repository;

import com.pgvpt.enums.CategorieOffre;
import com.pgvpt.enums.StatutOffre;
import com.pgvpt.model.OffreTouristiqueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OffreTouristiqueRepository extends JpaRepository<OffreTouristiqueEntity, UUID>,
        JpaSpecificationExecutor<OffreTouristiqueEntity> {

    boolean existsByNomIgnoreCase(String nom);

    boolean existsByNomIgnoreCaseAndIdNot(String nom, UUID id);

    List<OffreTouristiqueEntity> findByZoneTouristiqueId(UUID zoneTouristiqueId);

    List<OffreTouristiqueEntity> findByActeurId(UUID acteurId);

    List<OffreTouristiqueEntity> findByCircuitId(UUID circuitId);

    Page<OffreTouristiqueEntity> findByCategorie(CategorieOffre categorie, Pageable pageable);

    Page<OffreTouristiqueEntity> findByStatut(StatutOffre statut, Pageable pageable);

    Page<OffreTouristiqueEntity> findByZoneTouristiqueId(UUID zoneTouristiqueId, Pageable pageable);
}

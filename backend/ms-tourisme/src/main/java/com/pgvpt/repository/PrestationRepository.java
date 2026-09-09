package com.pgvpt.repository;

import com.pgvpt.model.PrestationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrestationRepository extends JpaRepository<PrestationEntity, UUID> {

    List<PrestationEntity> findByOffreIdOrderByCreatedAtAsc(UUID offreId);

    boolean existsByOffreIdAndNomIgnoreCase(UUID offreId, String nom);

    boolean existsByOffreIdAndNomIgnoreCaseAndIdNot(UUID offreId, String nom, UUID id);

    long countByOffreId(UUID offreId);

    void deleteByOffreId(UUID offreId);
}

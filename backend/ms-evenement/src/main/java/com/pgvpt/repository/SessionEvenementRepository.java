package com.pgvpt.repository;

import com.pgvpt.model.SessionEvenementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface SessionEvenementRepository extends JpaRepository<SessionEvenementEntity, UUID> {

    List<SessionEvenementEntity> findByEvenementIdOrderByDateDebutAsc(UUID evenementId);

    boolean existsByEvenementIdAndDateDebutLessThanAndDateFinGreaterThan(UUID evenementId, Instant dateFin, Instant dateDebut);

    long countByEvenementId(UUID evenementId);

    void deleteByEvenementId(UUID evenementId);
}
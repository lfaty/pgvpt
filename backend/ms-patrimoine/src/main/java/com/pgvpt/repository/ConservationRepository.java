package com.pgvpt.repository;

import com.pgvpt.model.ConservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ConservationRepository extends JpaRepository<ConservationEntity, UUID> {

    Optional<ConservationEntity> findByPatrimoineId(UUID patrimoineId);

    boolean existsByPatrimoineId(UUID patrimoineId);
}
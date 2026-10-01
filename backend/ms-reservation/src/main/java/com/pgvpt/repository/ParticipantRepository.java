package com.pgvpt.repository;

import com.pgvpt.model.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity,UUID> {

    List<ParticipantEntity> findByReservationIdOrderByNomAscPrenomAsc(UUID id);
    long countByReservationId(UUID id);
    void deleteByReservationId(UUID id);
}

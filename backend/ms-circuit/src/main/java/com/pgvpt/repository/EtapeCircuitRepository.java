package com.pgvpt.repository;

import com.pgvpt.model.EtapeCircuitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EtapeCircuitRepository extends JpaRepository<EtapeCircuitEntity, UUID> {

    List<EtapeCircuitEntity> findByCircuitIdOrderByOrdreAsc(UUID circuitId);

    long countByCircuitId(UUID circuitId);

    boolean existsByCircuitIdAndOrdre(UUID circuitId, Integer ordre);

    boolean existsByCircuitIdAndOrdreAndIdNot(UUID circuitId, Integer ordre, UUID id);

    void deleteByCircuitId(UUID circuitId);
}

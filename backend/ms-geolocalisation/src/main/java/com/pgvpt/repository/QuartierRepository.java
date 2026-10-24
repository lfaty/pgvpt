package com.pgvpt.repository;

import com.pgvpt.model.PaysEntity;
import com.pgvpt.model.QuartierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuartierRepository extends JpaRepository<QuartierEntity, UUID> {
    boolean existsByCode(String code);
    List<QuartierEntity> findByCommune_Id(UUID communeId);
    Page<QuartierEntity> findByCommune_Id(UUID communeId, Pageable pageable);
    List<QuartierEntity> findByVillage_Id(UUID villageId);
    boolean existsByVillage_Id(UUID villageId);
    boolean existsByCommune_Id(UUID communeId);
}

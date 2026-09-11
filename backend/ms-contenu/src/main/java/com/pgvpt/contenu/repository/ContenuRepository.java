package com.pgvpt.contenu.repository;

import com.pgvpt.contenu.model.ContenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ContenuRepository extends JpaRepository<ContenuEntity, UUID> {

    @Query("SELECT c FROM ContenuEntity c WHERE " +
           "(:patrimoineId IS NULL OR c.patrimoineId = :patrimoineId) AND " +
           "(:langue IS NULL OR c.langue = :langue) AND " +
           "(:statut IS NULL OR c.statut = :statut)")
    List<ContenuEntity> findByFilters(
            @Param("patrimoineId") UUID patrimoineId,
            @Param("langue") String langue,
            @Param("statut") com.pgvpt.contenu.enums.Statut statut);
}

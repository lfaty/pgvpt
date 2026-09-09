package com.pgvpt.contenu.repository;

import com.pgvpt.contenu.model.ContenuEntity;
import com.pgvpt.dto.StatutContenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContenuRepository extends JpaRepository<ContenuEntity, UUID> {
    List<ContenuEntity> findByPatrimoineIdLangueAndStatut(UUID patrimoineId, String langue, StatutContenu statut);
}

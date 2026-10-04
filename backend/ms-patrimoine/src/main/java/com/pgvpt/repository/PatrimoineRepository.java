package com.pgvpt.repository;

import com.pgvpt.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.pgvpt.model.PatrimoineEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PatrimoineRepository extends JpaRepository<PatrimoineEntity, UUID>, JpaSpecificationExecutor<PatrimoineEntity> {

    Optional<PatrimoineEntity> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, UUID id);

    List<PatrimoineEntity> findByStatut(StatutPatrimoineMetier statut);

    List<PatrimoineEntity> findByCategorie(CategoriePatrimoineMetier categorie);

    List<PatrimoineEntity> findByType(TypePatrimoineMetier type);
}
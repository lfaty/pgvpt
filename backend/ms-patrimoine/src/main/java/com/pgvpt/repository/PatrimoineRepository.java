package com.pgvpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.pgvpt.model.PatrimoineEntity;

import java.util.UUID;

@Repository
public interface PatrimoineRepository extends JpaRepository<PatrimoineEntity, UUID>, JpaSpecificationExecutor<PatrimoineEntity> {
}

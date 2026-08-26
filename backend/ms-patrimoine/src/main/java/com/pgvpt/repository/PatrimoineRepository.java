package com.pgvpt.repository;

import com.pgvpt.entities.PatrimoineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatrimoineRepository extends JpaRepository<PatrimoineEntity, UUID> {
}

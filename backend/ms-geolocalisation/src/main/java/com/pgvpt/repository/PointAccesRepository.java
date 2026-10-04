package com.pgvpt.repository;

import com.pgvpt.model.PointAccesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PointAccesRepository extends JpaRepository<PointAccesEntity, UUID> {

    List<PointAccesEntity> findByPatrimoineId(UUID patrimoineId);
}
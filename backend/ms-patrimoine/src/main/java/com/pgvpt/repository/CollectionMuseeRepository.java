package com.pgvpt.repository;

import com.pgvpt.model.CollectionMuseeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollectionMuseeRepository extends JpaRepository<CollectionMuseeEntity, UUID> {
}

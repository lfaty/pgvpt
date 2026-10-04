package com.pgvpt.repository;

import com.pgvpt.model.SiteNaturelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SiteNaturelRepository extends JpaRepository<SiteNaturelEntity, UUID> {
}

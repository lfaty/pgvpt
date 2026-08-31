package com.pgvpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvpt.model.HoraireOuvertureEntity;

import java.util.UUID;

@Repository
public interface HoraireRepository extends JpaRepository<HoraireOuvertureEntity, UUID> {
}

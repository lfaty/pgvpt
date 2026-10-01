package com.pgvpt.repository;

import com.pgvpt.model.ContactConservateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactConservateurRepository extends JpaRepository<ContactConservateurEntity, UUID> {
}

package com.pgvpt.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false) // Empêche la modification lors d'un UPDATE
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false) // Optionnel : vide à l'insertion, mis à jour QUE lors des UPDATE
    private LocalDateTime updatedAt;
}


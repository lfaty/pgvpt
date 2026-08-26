package com.pgvpt.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patrimoines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatrimoineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ===== Champs communs (PatrimoineBase) =====

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String type;  // SITE_NATUREL, MUSEE, MONUMENT

    @Column(nullable = false)
    private String categorie;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // ===== Champs spécifiques SiteNaturel =====
    private Double superficie;
    private String natureSite;
    private String ecosysteme;

    // ===== Champs spécifiques Musée =====
    private Integer nombreCollections;

    // ===== Champs spécifiques Monument =====
    private String styleArchitectural;
    private String natureMonument;

    // ===== Timestamps =====
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

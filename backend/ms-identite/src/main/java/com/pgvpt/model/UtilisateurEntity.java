package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.StatutUtilisateur;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "utilisateurs")
public class UtilisateurEntity extends BaseEntity {

    private String username;
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private StatutUtilisateur statut;

    private boolean actif;

    private Instant derniereConnexion;

    @ManyToMany
    private Set<RoleEntity> roles;

}

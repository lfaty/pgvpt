package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profils")
public class ProfilEntity extends BaseEntity {

    @OneToOne
    private UtilisateurEntity utilisateur;

    private String prenom;
    private String nom;
    private String telephone;
    private String langue;
    private String pays;
}

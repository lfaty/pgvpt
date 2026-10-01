package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    private String code;
    private String libelle;
    private String description;
}

package com.pgvpt.model;


import com.pgvpt.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contact_conservateurs")
@Getter
@Setter
public class ContactConservateurEntity extends BaseEntity {
    private String nom;
    private String fonction;
    private String telephone;
    private String email;
    private String siteWeb;
    private String organisme;
}

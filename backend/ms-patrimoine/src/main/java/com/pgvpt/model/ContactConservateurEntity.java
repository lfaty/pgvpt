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
    @Column(columnDefinition = "TEXT")
    private String nom;
    @Column(columnDefinition = "TEXT")
    private String fonction;
    @Column(columnDefinition = "TEXT")
    private String telephone;
    @Column(columnDefinition = "TEXT")
    private String email;
    @Column(columnDefinition = "TEXT")
    private String siteWeb;
    @Column(columnDefinition = "TEXT")
    private String organisme;
}

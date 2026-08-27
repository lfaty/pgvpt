package com.pgvpt.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
public class ContactEntity {

    public ContactEntity() {}

    public ContactEntity(String nom, String fonction, String telephone, String email, String siteWeb) {
        this.nom = nom;
        this.fonction = fonction;
        this.telephone = telephone;
        this.email = email;
        this.siteWeb = siteWeb;
    }
    private String nom;
    private String fonction;
    private String telephone;
    private String email;
    private String siteWeb;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getFonction() { return fonction; }
    public void setFonction(String fonction) { this.fonction = fonction; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSiteWeb() { return siteWeb; }
    public void setSiteWeb(String siteWeb) { this.siteWeb = siteWeb; }
}

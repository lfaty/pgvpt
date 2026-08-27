package com.pgvpt.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
public class TarificationEntity {

    public TarificationEntity() {}

    public TarificationEntity(Boolean gratuit, BigDecimal tarifNormal, String devise, BigDecimal tarifEnfant, BigDecimal tarifEtudiant, BigDecimal tarifResident, BigDecimal tarifGroupe, BigDecimal tarifGuide, String conditionsTarifaires) {
        this.gratuit = gratuit;
        this.tarifNormal = tarifNormal;
        this.devise = devise;
        this.tarifEnfant = tarifEnfant;
        this.tarifEtudiant = tarifEtudiant;
        this.tarifResident = tarifResident;
        this.tarifGroupe = tarifGroupe;
        this.tarifGuide = tarifGuide;
        this.conditionsTarifaires = conditionsTarifaires;
    }
    private Boolean gratuit;
    private BigDecimal tarifNormal;
    @Column(length = 10)
    private String devise;
    private BigDecimal tarifEnfant;
    private BigDecimal tarifEtudiant;
    private BigDecimal tarifResident;
    private BigDecimal tarifGroupe;
    private BigDecimal tarifGuide;
    @Column(columnDefinition = "TEXT")
    private String conditionsTarifaires;

    public Boolean getGratuit() { return gratuit; }
    public void setGratuit(Boolean gratuit) { this.gratuit = gratuit; }

    public BigDecimal getTarifNormal() { return tarifNormal; }
    public void setTarifNormal(BigDecimal tarifNormal) { this.tarifNormal = tarifNormal; }

    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public BigDecimal getTarifEnfant() { return tarifEnfant; }
    public void setTarifEnfant(BigDecimal tarifEnfant) { this.tarifEnfant = tarifEnfant; }

    public BigDecimal getTarifEtudiant() { return tarifEtudiant; }
    public void setTarifEtudiant(BigDecimal tarifEtudiant) { this.tarifEtudiant = tarifEtudiant; }

    public BigDecimal getTarifResident() { return tarifResident; }
    public void setTarifResident(BigDecimal tarifResident) { this.tarifResident = tarifResident; }

    public BigDecimal getTarifGroupe() { return tarifGroupe; }
    public void setTarifGroupe(BigDecimal tarifGroupe) { this.tarifGroupe = tarifGroupe; }

    public BigDecimal getTarifGuide() { return tarifGuide; }
    public void setTarifGuide(BigDecimal tarifGuide) { this.tarifGuide = tarifGuide; }

    public String getConditionsTarifaires() { return conditionsTarifaires; }
    public void setConditionsTarifaires(String conditionsTarifaires) { this.conditionsTarifaires = conditionsTarifaires; }
}

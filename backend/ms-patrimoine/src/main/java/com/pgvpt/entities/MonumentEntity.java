package com.pgvpt.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "monuments")
public class MonumentEntity extends PatrimoineEntity {

    public MonumentEntity() {}

    public MonumentEntity(String styleArchitectural, String natureMonument, Integer anneeConstruction, String identiteArchitecte) {
        this.styleArchitectural = styleArchitectural;
        this.natureMonument = natureMonument;
        this.anneeConstruction = anneeConstruction;
        this.identiteArchitecte = identiteArchitecte;
    }

    private String styleArchitectural;

    private String natureMonument;

    private Integer anneeConstruction;

    private String identiteArchitecte;

    public String getStyleArchitectural() { return styleArchitectural; }
    public void setStyleArchitectural(String styleArchitectural) { this.styleArchitectural = styleArchitectural; }

    public String getNatureMonument() { return natureMonument; }
    public void setNatureMonument(String natureMonument) { this.natureMonument = natureMonument; }

    public Integer getAnneeConstruction() { return anneeConstruction; }
    public void setAnneeConstruction(Integer anneeConstruction) { this.anneeConstruction = anneeConstruction; }

    public String getIdentiteArchitecte() { return identiteArchitecte; }
    public void setIdentiteArchitecte(String identiteArchitecte) { this.identiteArchitecte = identiteArchitecte; }
}

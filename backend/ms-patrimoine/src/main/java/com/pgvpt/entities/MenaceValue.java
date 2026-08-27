package com.pgvpt.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
public class MenaceValue {

    public MenaceValue() {}

    public MenaceValue(String type, String niveau, String description, String mesuresPrevention) {
        this.type = type;
        this.niveau = niveau;
        this.description = description;
        this.mesuresPrevention = mesuresPrevention;
    }
    private String type;
    private String niveau;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String mesuresPrevention;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMesuresPrevention() { return mesuresPrevention; }
    public void setMesuresPrevention(String mesuresPrevention) { this.mesuresPrevention = mesuresPrevention; }
}

package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pays")
@Getter
@Setter
public class PaysEntity extends BaseEntity {
    private String nom;
    private String code;
    private  String codeIso2;
    private String codeIso3;
    private String devise;
    private String codeDevise;
    private String indicatifTelephonique;
    private String langueOfficielle;
    private String continent;
    private Boolean actif = true;

}


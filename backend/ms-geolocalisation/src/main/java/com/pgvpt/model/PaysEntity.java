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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaysEntity extends BaseEntity {
    @Id
    @GeneratedValue

    private UUID id;

    private String nom;

    private String code;

    private @Nullable String codeIso2;

    private @Nullable String codeIso3;

    private String devise;

    private @Nullable String codeDevise;

    private @Nullable String indicatifTelephonique;

    private @Nullable String langueOfficielle;

    private @Nullable String continent;

    private Boolean actif = true;

//    @OneToMany
//    private List<RegionReference> regionReferences;

}


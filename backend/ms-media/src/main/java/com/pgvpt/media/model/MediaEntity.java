package com.pgvpt.media.model;

import com.pgvpt.media.common.BaseEntity;
import com.pgvpt.media.enums.TypeMedia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="medias")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaEntity extends BaseEntity {
    private UUID patrimoineId;
    private TypeMedia type;
    private String nom;
    private String description;
    private String url;
    private String mimeType;
    private Long tailleOctets;
    private String langue;
    private String auteur;
    private String droitsUtilisation;
    private String credit;
    private BigDecimal latitude;
    private BigDecimal longitude;
}

package com.pgvpt.media.model;

import com.pgvpt.media.common.BaseEntity;
import com.pgvpt.media.enums.StatutMediaMetier;
import com.pgvpt.media.enums.TypeMediaMetier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="medias")
@Getter @Setter
public class MediaEntity extends BaseEntity {
    @Column(name = "patrimoine_id", nullable = false)
    private UUID patrimoineId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TypeMediaMetier type;
    private String nom;
    private String description;
    private String url;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "taille_octets")
    private Long tailleOctets;

    private String langue;
    private String auteur;

    @Column(name = "droits_utilisation")
    private String droitsUtilisation;

    private String credit;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutMediaMetier statut = StatutMediaMetier.BROUILLON;

    @Column(name = "checksum_sha256", length = 64)
    private String checksumSha256;


}

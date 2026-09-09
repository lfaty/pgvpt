package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import com.pgvpt.enums.TypePrestation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "prestations",
        indexes = {
                @Index(name = "idx_prestation_offre", columnList = "offre_id"
                ),
                @Index(name = "idx_prestation_type", columnList = "type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestationEntity extends BaseEntity {

    @Column(name = "offre_id", nullable = false)
    private UUID offreId;

    @Column(nullable = false, length = 200)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TypePrestation type;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal prix = BigDecimal.ZERO;

    @Column(nullable = false, length = 3)
    private String devise = "XOF";

    @Column(nullable = false)
    private boolean actif = true;
}

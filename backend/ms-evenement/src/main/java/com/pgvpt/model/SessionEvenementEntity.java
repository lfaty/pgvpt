package com.pgvpt.model;

import com.pgvpt.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "sessions_evenement",
        indexes = {
                @Index(
                        name = "idx_session_evenement",
                        columnList = "evenement_id"
                ),
                @Index(
                        name = "idx_session_date",
                        columnList = "date_debut,date_fin"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionEvenementEntity extends BaseEntity {

    @Column(name = "evenement_id", nullable = false)
    private UUID evenementId;

    @Column(name = "date_debut", nullable = false)
    private Instant dateDebut;

    @Column(name = "date_fin", nullable = false)
    private Instant dateFin;

    @Column
    private Integer capacite;

    @Column(length = 500)
    private String lieu;

    @Column(name = "patrimoine_id")
    private UUID patrimoineId;
}

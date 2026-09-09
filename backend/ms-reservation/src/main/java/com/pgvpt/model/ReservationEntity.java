package com.pgvpt.model;
import com.pgvpt.common.BaseEntity;

import com.pgvpt.enums.StatutReservation;
import com.pgvpt.enums.TypeRessource;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="reservations",
        indexes={@Index(name="idx_res_user",columnList="utilisateur_id"),
                @Index(name="idx_res_resource",columnList="type_ressource,ressource_id"),
                @Index(name="idx_res_status",columnList="statut")})
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity extends BaseEntity {

     @Column(nullable=false,unique=true,length=40)
     private String numero;

     @Column(name="utilisateur_id",nullable=false)
     private UUID utilisateurId;

     @Enumerated(EnumType.STRING) @Column(name="type_ressource",nullable=false,length=30)
     private TypeRessource typeRessource;

     @Column(name="ressource_id",nullable=false)
     private UUID ressourceId;

     @Column(name="date_reservation",nullable=false)
     private Instant dateReservation;

     @Column(name="nombre_personnes",nullable=false)
     private Integer nombrePersonnes;

     @Column(nullable=false,length=3)
     private String devise="XOF";

     @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
     private StatutReservation statut=StatutReservation.EN_ATTENTE;

     private Instant dateConfirmation;

     private Instant dateAnnulation;

     @Column(name="motif_annulation",length=1000)
     private String motifAnnulation;

     @Column(length=2000)
     private String commentaire;

     @Column(name="idempotency_key",length=100,unique=true)
     private String idempotencyKey;
}
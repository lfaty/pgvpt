package com.pgvpt.model;
import com.pgvpt.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name="participants", indexes=@Index(name="idx_part_reservation",columnList="reservation_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantEntity extends BaseEntity {

     @ManyToOne(fetch = FetchType.LAZY, optional = false)
     @JoinColumn(name = "reservation_id", nullable = false)
     private ReservationEntity reservation;

     @Column(nullable=false,length=100)
     private String nom;

     @Column(nullable=false,length=100)
     private String prenom;

     @Column(length=30)
     private String telephone;

     @Column(length=254) private String email;
     private Integer age;
}
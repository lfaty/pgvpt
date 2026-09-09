package com.pgvpt.repository;


import com.pgvpt.enums.StatutReservation;
import com.pgvpt.enums.TypeRessource;
import com.pgvpt.model.ReservationEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.*;

public interface ReservationRepository extends JpaRepository<ReservationEntity,UUID> {

    Optional<ReservationEntity> findByNumero(String numero);

    @Query("select r from ReservationEntity r where (:utilisateurId is null or r.utilisateurId=:utilisateurId) " +
            "and (:statut is null or r.statut=:statut) " +
            "and (:typeRessource is null or r.typeRessource=:typeRessource) " +
            "and (:ressourceId is null or r.ressourceId=:ressourceId) " +
            "and (:dateDebut is null or r.dateReservation>=:dateDebut) " +
            "and (:dateFin is null or r.dateReservation<=:dateFin)")
    Page<ReservationEntity> rechercher(@Param("utilisateurId") UUID u,
                                       @Param("statut") StatutReservation s,
                                       @Param("typeRessource") TypeRessource t,
                                       @Param("ressourceId") UUID r,
                                       @Param("dateDebut") Instant d1,
                                       @Param("dateFin") Instant d2, Pageable p);

    @Query("select coalesce(sum(r.nombrePersonnes),0) from ReservationEntity r where r.typeRessource=:typeRessource " +
            "and r.ressourceId=:ressourceId and r.dateReservation=:dateReservation " +
            "and r.statut in :statuts")
    long countPersonnesReservees(@Param("typeRessource") TypeRessource t,
                                 @Param("ressourceId") UUID r,
                                 @Param("dateReservation") Instant d,
                                 @Param("statuts") Collection<StatutReservation> s);
}

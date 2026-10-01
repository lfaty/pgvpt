package com.pgvpt.repository;


import com.pgvpt.enums.StatutReservation;
import com.pgvpt.enums.TypeRessource;
import com.pgvpt.model.ReservationEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.*;

import jakarta.persistence.criteria.Predicate;

public interface ReservationRepository extends JpaRepository<ReservationEntity,UUID>, JpaSpecificationExecutor<ReservationEntity> {

    Optional<ReservationEntity> findByNumero(String numero);

    default Page<ReservationEntity> rechercher(UUID u, StatutReservation s, TypeRessource t, UUID r, Instant d1, Instant d2, Pageable p) {
        return findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (u != null) predicates.add(cb.equal(root.get("utilisateurId"), u));
            if (s != null) predicates.add(cb.equal(root.get("statut"), s));
            if (t != null) predicates.add(cb.equal(root.get("typeRessource"), t));
            if (r != null) predicates.add(cb.equal(root.get("ressourceId"), r));
            if (d1 != null) predicates.add(cb.greaterThanOrEqualTo(root.get("dateReservation"), d1));
            if (d2 != null) predicates.add(cb.lessThanOrEqualTo(root.get("dateReservation"), d2));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, p);
    }

    @Query("select coalesce(sum(r.nombrePersonnes),0) from ReservationEntity r where r.typeRessource=:typeRessource " +
            "and r.ressourceId=:ressourceId and r.dateReservation=:dateReservation " +
            "and r.statut in :statuts")
    long countPersonnesReservees(@Param("typeRessource") TypeRessource t,
                                 @Param("ressourceId") UUID r,
                                 @Param("dateReservation") Instant d,
                                 @Param("statuts") Collection<StatutReservation> s);
}

package com.pgvpt.service.impl;

import com.pgvpt.model.PatrimoineEntity;
import com.pgvpt.record.PatrimoineSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class PatrimoineSpecifications {

    private PatrimoineSpecifications() {
    }

    public static Specification<PatrimoineEntity> search(PatrimoineSearchCriteria criteria) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (criteria.q() != null && !criteria.q().isBlank()) {

                String pattern =
                        "%" + criteria.q().toLowerCase() + "%";

                Predicate nom = cb.like(
                        cb.lower(root.get("nom")),
                        pattern
                );

                Predicate description = cb.like(
                        cb.lower(root.get("description")),
                        pattern
                );

                Predicate historique = cb.like(
                        cb.lower(root.get("historique")),
                        pattern
                );

                predicates.add(
                        cb.or(nom, description, historique)
                );
            }

            if (criteria.type() != null) {
                predicates.add(
                        cb.equal(root.get("type"), criteria.type())
                );
            }

            if (criteria.categorie() != null) {
                predicates.add(
                        cb.equal(root.get("categorie"), criteria.categorie())
                );
            }

            if (criteria.statut() != null) {
                predicates.add(
                        cb.equal(root.get("statut"), criteria.statut())
                );
            }

            if (criteria.etatConservation() != null) {
                predicates.add(
                        cb.equal(root.get("etatConservation"), criteria.etatConservation())
                );
            }

            if (criteria.accessiblePublic() != null) {
                predicates.add(
                        cb.equal(root.get("accessiblePublic"), criteria.accessiblePublic())
                );
            }

            if (criteria.inscritUnesco() != null) {
                predicates.add(
                        cb.equal(root.get("inscritUnesco"), criteria.inscritUnesco())
                );
            }

            if (criteria.classePatrimoine() != null) {
                predicates.add(
                        cb.equal(root.get("classePatrimoine"), criteria.classePatrimoine())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
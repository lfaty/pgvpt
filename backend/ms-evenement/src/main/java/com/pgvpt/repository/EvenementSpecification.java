package com.pgvpt.repository;

import com.pgvpt.enums.StatutEvenement;
import com.pgvpt.enums.TypeEvenement;
import com.pgvpt.model.EvenementEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EvenementSpecification {

    private EvenementSpecification() {
    }

    public static Specification<EvenementEntity> search(
            UUID zoneTouristiqueId,
            TypeEvenement type,
            StatutEvenement statut,
            Instant dateDebut,
            Instant dateFin,
            String search) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (zoneTouristiqueId != null) {
                predicates.add(cb.equal(root.get("zoneTouristiqueId"), zoneTouristiqueId));
            }

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            if (statut != null) {
                predicates.add(cb.equal(root.get("statut"), statut));
            }

            if (dateDebut != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), dateDebut));
            }

            if (dateFin != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), dateFin));
            }

            if (search != null && !search.isBlank()) {
                String pattern = "%" + search.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("titre")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

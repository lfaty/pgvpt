package com.pgvpt.repository;

import com.pgvpt.model.OffreTouristiqueEntity;
import org.springframework.data.jpa.domain.Specification;
import com.pgvpt.enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.criteria.Predicate;

public final class OffreTouristiqueSpecification {

    private OffreTouristiqueSpecification() { }

    public static Specification<OffreTouristiqueEntity> search(
            String search,
            UUID zoneTouristiqueId,
            UUID acteurId,
            UUID circuitId,
            CategorieOffre categorie,
            StatutOffre statut) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {

                String pattern = "%" + search.toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("nom")), pattern));
            }

            if (zoneTouristiqueId != null) {
                predicates.add(cb.equal(root.get("zoneTouristiqueId"), zoneTouristiqueId));
            }

            if (acteurId != null) {
                predicates.add(cb.equal(root.get("acteurId"), acteurId));
            }

            if (circuitId != null) {
                predicates.add(cb.equal(root.get("circuitId"), circuitId));
            }

            if (categorie != null) {
                predicates.add(cb.equal(root.get("categorie"), categorie));
            }

            if (statut != null) {
                predicates.add(cb.equal(root.get("statut"), statut));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

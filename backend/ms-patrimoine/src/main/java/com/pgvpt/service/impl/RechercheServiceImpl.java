package com.pgvpt.service.impl;

import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.PatrimoineSummary;
import com.pgvpt.dto.TypePatrimoine;
import com.pgvpt.model.PatrimoineEntity;
import com.pgvpt.repository.PatrimoineRepository;
import com.pgvpt.service.RechercheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RechercheServiceImpl implements RechercheService {

    private final PatrimoineRepository patrimoineRepository;

    @Override
    public List<PatrimoineSummary> rechercherPatrimoines(String q, TypePatrimoine type, CategoriePatrimoine categorie, String region, String departement, String commune) {
        List<PatrimoineEntity> entities = patrimoineRepository.findAll();
        String needle = q == null ? null : q.trim().toLowerCase(java.util.Locale.ROOT);
        return entities.stream()
                .filter(entity -> type == null || type.getValue().equals(entity.getType()))
                .filter(entity -> categorie == null || categorie.getValue().equals(entity.getCategorie()))
                .filter(entity -> needle == null || needle.isEmpty() || contains(entity.getNom(), needle) || contains(entity.getCode(), needle) || contains(entity.getDescription(), needle))
                .map(this::mapToSummary).collect(Collectors.toList());
    }

    private PatrimoineSummary mapToSummary(PatrimoineEntity entity) {
        PatrimoineSummary summary = new PatrimoineSummary();
        summary.setId(entity.getId());
        summary.setNom(entity.getNom());
        
        if (entity.getType() != null) {
            try { summary.setType(TypePatrimoine.fromValue(entity.getType())); } catch (IllegalArgumentException ignored) { }
        }
        if (entity.getCategorie() != null) {
            try { summary.setCategorie(CategoriePatrimoine.fromValue(entity.getCategorie())); } catch (IllegalArgumentException ignored) { }
        }
        summary.setDescriptionCourte(entity.getDescriptionCourte());
        
        // Add main photo URL if available
        if (entity.getPhotos() != null && !entity.getPhotos().isEmpty()) {
            try {
                summary.setPhotoPrincipale(new java.net.URI(entity.getPhotos().get(0).getUrl()));
            } catch (Exception e) {
                // Ignore invalid URI
            }
        }
        
        return summary;
    }

    private boolean contains(String value, String needle) {
        return value != null && value.toLowerCase(java.util.Locale.ROOT).contains(needle);
    }
}

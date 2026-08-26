package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.entities.PatrimoineEntity;
import com.pgvpt.exception.PatrimoineValidationException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Mapper manuel gérant le polymorphisme PatrimoineCreate → PatrimoineEntity → Patrimoine.
 *
 * MapStruct ne gère pas facilement les interfaces polymorphes (oneOf),
 * d'où le choix d'un mapper manuel avec instanceof / switch.
 */
@Component
public class PatrimoineMapper {

    // ═══════════════════════════════════════════════════════════
    // PatrimoineCreate (DTO entrée) → PatrimoineEntity
    // ═══════════════════════════════════════════════════════════

    public PatrimoineEntity toEntity(PatrimoineCreate dto) {
        if (dto instanceof SiteNaturelCreate site) {
            return mapSiteNaturelToEntity(site);
        } else if (dto instanceof MuseeCreate musee) {
            return mapMuseeToEntity(musee);
        } else if (dto instanceof MonumentCreate monument) {
            return mapMonumentToEntity(monument);
        }
        throw new PatrimoineValidationException("Type de patrimoine non supporté");
    }

    private PatrimoineEntity mapSiteNaturelToEntity(SiteNaturelCreate dto) {
        PatrimoineEntity entity = mapCommonToEntity(
                dto.getNom(), dto.getType(), dto.getCategorie(), dto.getDescription());
        entity.setSuperficie(dto.getSuperficie());
        entity.setNatureSite(dto.getNatureSite() != null ? dto.getNatureSite().getValue() : null);
        entity.setEcosysteme(dto.getEcosysteme());
        return entity;
    }

    private PatrimoineEntity mapMuseeToEntity(MuseeCreate dto) {
        PatrimoineEntity entity = mapCommonToEntity(
                dto.getNom(), dto.getType(), dto.getCategorie(), dto.getDescription());
        entity.setNombreCollections(dto.getNombreCollections());
        return entity;
    }

    private PatrimoineEntity mapMonumentToEntity(MonumentCreate dto) {
        PatrimoineEntity entity = mapCommonToEntity(
                dto.getNom(), dto.getType(), dto.getCategorie(), dto.getDescription());
        entity.setStyleArchitectural(dto.getStyleArchitectural());
        entity.setNatureMonument(dto.getNatureMonument() != null ? dto.getNatureMonument().getValue() : null);
        return entity;
    }

    private PatrimoineEntity mapCommonToEntity(String nom, String type,
                                                CategoriePatrimoine categorie, String description) {
        PatrimoineEntity entity = new PatrimoineEntity();
        entity.setNom(nom);
        entity.setType(type);
        entity.setCategorie(categorie != null ? categorie.getValue() : null);
        entity.setDescription(description);
        return entity;
    }

    // ═══════════════════════════════════════════════════════════
    // PatrimoineEntity → Patrimoine (DTO réponse)
    // ═══════════════════════════════════════════════════════════

    public Patrimoine toDto(PatrimoineEntity entity) {
        return switch (entity.getType()) {
            case "SITE_NATUREL" -> mapEntityToSiteNaturel(entity);
            case "MUSEE" -> mapEntityToMusee(entity);
            case "MONUMENT" -> mapEntityToMonument(entity);
            default -> throw new PatrimoineValidationException("Type de patrimoine inconnu : " + entity.getType());
        };
    }

    private SiteNaturel mapEntityToSiteNaturel(PatrimoineEntity entity) {
        SiteNaturel dto = new SiteNaturel();
        setCommonDtoFields(dto.id(entity.getId())
                .nom(entity.getNom())
                .type(entity.getType())
                .description(entity.getDescription()), entity);
        dto.setCategorie(CategoriePatrimoine.fromValue(entity.getCategorie()));
        dto.setSuperficie(entity.getSuperficie());
        if (entity.getNatureSite() != null) {
            dto.setNatureSite(NatureSiteNaturel.fromValue(entity.getNatureSite()));
        }
        dto.setEcosysteme(entity.getEcosysteme());
        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt().atOffset(ZoneOffset.UTC));
        }
        if (entity.getUpdatedAt() != null) {
            dto.setUpdatedAt(entity.getUpdatedAt().atOffset(ZoneOffset.UTC));
        }
        return dto;
    }

    private Musee mapEntityToMusee(PatrimoineEntity entity) {
        Musee dto = new Musee();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setType(entity.getType());
        dto.setCategorie(CategoriePatrimoine.fromValue(entity.getCategorie()));
        dto.setDescription(entity.getDescription());
        dto.setNombreCollections(entity.getNombreCollections());
        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt().atOffset(ZoneOffset.UTC));
        }
        if (entity.getUpdatedAt() != null) {
            dto.setUpdatedAt(entity.getUpdatedAt().atOffset(ZoneOffset.UTC));
        }
        return dto;
    }

    private Monument mapEntityToMonument(PatrimoineEntity entity) {
        Monument dto = new Monument();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setType(entity.getType());
        dto.setCategorie(CategoriePatrimoine.fromValue(entity.getCategorie()));
        dto.setDescription(entity.getDescription());
        dto.setStyleArchitectural(entity.getStyleArchitectural());
        if (entity.getNatureMonument() != null) {
            dto.setNatureMonument(NatureMonument.fromValue(entity.getNatureMonument()));
        }
        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt().atOffset(ZoneOffset.UTC));
        }
        if (entity.getUpdatedAt() != null) {
            dto.setUpdatedAt(entity.getUpdatedAt().atOffset(ZoneOffset.UTC));
        }
        return dto;
    }

    /** Helper : les DTO réponse n'ont pas de superclasse commune pour les setters */
    private void setCommonDtoFields(Object dto, PatrimoineEntity entity) {
        // Les champs communs sont déjà settés via les fluent methods dans l'appelant
    }
}

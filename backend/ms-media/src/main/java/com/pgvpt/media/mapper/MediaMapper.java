package com.pgvpt.media.mapper;


import com.pgvpt.dto.*;
import com.pgvpt.media.enums.StatutMediaMetier;
import com.pgvpt.media.enums.TypeMediaMetier;
import com.pgvpt.media.model.MediaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ValueMapping;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MediaMapper {

    // --- Vers Entity (Création / Mutation) ---
    MediaEntity toEntity(MediaCreate dto);

    // --- Vers DTO (Lecture) ---
    Media toDto(MediaEntity entity);

    List<Media> toDtoList(List<MediaEntity> entities);

    // --- Updates In-Place (Put / Patch) ---
    void updateEntity(MediaCreate dto, @MappingTarget MediaEntity entity);

    // --- UTILITAIRES DE MAPPING INTERNE (URI <-> String) ---
    default String map(URI value) {
        return value == null ? null : value.toString();
    }

    default URI map(String value) {
        if (value == null || value.isBlank()) { return null; }
        try {
            return URI.create(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // --- MAPPINGS D'ENUMS (CORRIGÉ POUR MODELE_3_D) ---
    @ValueMapping(source = "MODELE_3_D", target = "MODELE_3D") // Force la correspondance malgré l'underscore
    TypeMediaMetier toTypeMetier(TypeMedia source);

    @ValueMapping(source = "MODELE_3D", target = "MODELE_3_D") // Mapping inverse pour la lecture
    TypeMedia toTypeDto(TypeMediaMetier source);

    default StatutMediaMetier toStatutMetier(String source) {
        if (source == null) return StatutMediaMetier.BROUILLON;
        try {
            return StatutMediaMetier.valueOf(source);
        } catch (IllegalArgumentException e) {
            return StatutMediaMetier.BROUILLON;
        }
    }

    default String toStatutDto(StatutMediaMetier source) {
        return source == null ? null : source.name();
    }

    // --- UTILITAIRES TEMPORELS UNIQUES (Instant <-> OffsetDateTime) ---
    default OffsetDateTime map(Instant value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    default Instant map(OffsetDateTime value) {
        return value == null ? null : value.toInstant();
    }
}
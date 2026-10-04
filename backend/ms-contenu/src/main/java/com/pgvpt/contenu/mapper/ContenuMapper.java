package com.pgvpt.contenu.mapper;

import com.pgvpt.contenu.enums.StatutContenuMetier;
import com.pgvpt.contenu.enums.TypeContenuMetier;
import com.pgvpt.dto.*;
import com.pgvpt.contenu.model.ContenuEntity;
import org.mapstruct.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.List;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContenuMapper {

    // --- Vers Entity (Mutation) ---
    // CORRECTION : On retire les lignes sur createdAt/updatedAt car le DTO source "ContenuCreate" ne les possède pas.
    // On ignore ces cibles ou on laisse JPA les générer via @PrePersist.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "datePublication", ignore = true)
    ContenuEntity toEntity(ContenuCreate dto);

    // --- Vers DTO (Lecture) ---
    @Mapping(target = "createdAt", qualifiedByName = "instantToOffset")
    @Mapping(target = "updatedAt", qualifiedByName = "instantToOffset")
    @Mapping(target = "datePublication", qualifiedByName = "localDateTimeToOffset")
    Contenu toDto(ContenuEntity entity);

    List<Contenu> toDtoList(List<ContenuEntity> entities);

    // --- Updates In-Place (Modification) ---
    // CORRECTION : Même logique, le DTO "ContenuUpdate" ne possède pas "createdAt" ni "updatedAt".
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "datePublication", ignore = true)
    void updateEntity(ContenuUpdate dto, @MappingTarget ContenuEntity entity);

    // --- Mappings d'Enums ---
    TypeContenuMetier toTypeMetier(TypeContenu source);
    TypeContenu toTypeDto(TypeContenuMetier source);

    StatutContenuMetier toStatutMetier(StatutContenu source);
    StatutContenu toStatutDto(StatutContenuMetier source);

    // --- Convertisseurs d'identifiants ---
    default String map(UUID value) { return value == null ? null : value.toString(); }
    default UUID map(String value) { return value == null || value.isBlank() ? null : UUID.fromString(value); }

    // --- CONVERTISSEURS TEMPORELS NOMMÉS ---

    @Named("instantToOffset")
    default OffsetDateTime instantToOffset(Instant value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    @Named("offsetToInstant")
    default Instant offsetToInstant(OffsetDateTime value) {
        return value == null ? null : value.toInstant();
    }

    @Named("localDateTimeToOffset")
    default OffsetDateTime localDateTimeToOffset(LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    @Named("offsetToLocalDateTime")
    default LocalDateTime offsetToLocalDateTime(OffsetDateTime value) {
        return value == null ? null : value.toLocalDateTime();
    }
}

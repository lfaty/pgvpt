package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.ZoneGeographiqueEntity;
import com.pgvpt.model.ZoneGeographiqueRegionEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ZoneGeographiqueApiMapper {

    /**
     * Convertit une entité en DTO de réponse.
     * Les regionLinks (entité de jointure) sont mappés en ZoneGeographiqueRegion
     * avec id, createdAt, updatedAt remplis.
     */
    @Mapping(target = "regions", expression = "java(toZoneGeographiqueRegions(entity))")
    ZoneGeographique toApi(ZoneGeographiqueEntity entity);

    /**
     * Convertit un DTO de création en entité squelette (sans les régions).
     * Le service gère la création des ZoneGeographiqueRegionEntity.
     */
    @Mapping(target = "regionLinks", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    ZoneGeographiqueEntity toEntity(ZoneGeographiqueCreate dto);

    @Mapping(target = "regionLinks", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    ZoneGeographiqueEntity toEntity(ZoneGeographiqueUpdate dto);

    ZoneGeographiqueReference toReference(ZoneGeographiqueEntity entity);

    /**
     * Convertit une entité de jointure en DTO ZoneGeographiqueRegion.
     * Fournit id, zoneGeographiqueId, regionId, createdAt et updatedAt.
     */
    default ZoneGeographiqueRegion toZoneGeographiqueRegion(ZoneGeographiqueRegionEntity link) {
        if (link == null) return null;
        ZoneGeographiqueRegion dto = new ZoneGeographiqueRegion();
        dto.setId(link.getId());
        dto.setZoneGeographiqueId(link.getZoneGeographique() != null ? link.getZoneGeographique().getId() : null);
        dto.setRegionId(link.getRegion() != null ? link.getRegion().getId() : null);
        dto.setCreatedAt(map(link.getCreatedAt()));
        dto.setUpdatedAt(map(link.getUpdatedAt()));
        return dto;
    }

    default List<ZoneGeographiqueRegion> toZoneGeographiqueRegions(ZoneGeographiqueEntity entity) {
        if (entity == null || entity.getRegionLinks() == null) return List.of();
        return entity.getRegionLinks().stream().map(this::toZoneGeographiqueRegion).toList();
    }

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) return null;
        return value.atOffset(ZoneOffset.UTC);
    }
}


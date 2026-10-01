package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.ZoneGeographiqueEntity;
import com.pgvpt.model.ZoneGeographiqueRegionEntity;
import com.pgvpt.model.ZoneTouristiqueEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        builder = @Builder(disableBuilder = true)
)
public interface ZoneMapper {

    // ==========================================
    // ZONE GEOGRAPHIQUE MAPPINGS
    // ==========================================
    @Mapping(target = "regionLinks", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ZoneGeographiqueEntity toEntityGeo(ZoneGeographiqueCreate dto);

    @Mapping(target = "regionLinks", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityGeo(ZoneGeographiqueUpdate dto, @MappingTarget ZoneGeographiqueEntity entity);

    @Mapping(target = "regions", expression = "java(toZoneGeographiqueRegions(entity))")
    ZoneGeographique toDtoGeo(ZoneGeographiqueEntity entity);

    ZoneGeographiqueReference toReference(ZoneGeographiqueEntity entity);


    // ==========================================
    // ZONE TOURISTIQUE MAPPINGS
    // ==========================================
    @Mapping(target = "zoneGeographique", ignore = true) // Ignoré car résolu via l'ID ou mappé manuellement dans le service
    @Mapping(target = "departement", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "village", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ZoneTouristiqueEntity toEntityTour(ZoneTouristiqueCreate dto);

    @Mapping(target = "zoneGeographique", ignore = true)
    @Mapping(target = "departement", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "village", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityTour(ZoneTouristiqueUpdate dto, @MappingTarget ZoneTouristiqueEntity entity);

    @Mapping(target = "zoneGeographiqueId", source = "zoneGeographique.id")
    @Mapping(target = "zoneGeographique", source = "zoneGeographique")
    @Mapping(target = "departementId", source = "departement.id")
    @Mapping(target = "departement", source = "departement")
    @Mapping(target = "communeId", source = "commune.id")
    @Mapping(target = "villageId", source = "village.id")
    ZoneTouristique toDtoTour(ZoneTouristiqueEntity entity);

    ZoneTouristiqueReference toReference(ZoneTouristiqueEntity entity);

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

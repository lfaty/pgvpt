package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.*;
import org.mapstruct.*;


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {ReferentielAdministratifApiMapper.class}
)
public interface ZoneTouristiqueApiMapper {

    /**
     * Convertit une entité en DTO de réponse.
     * Les enums TypeZone ont maintenant les mêmes noms → mapping automatique.
     * Les références (zoneGeographique, departement) sont mappées via le mapper référentiel.
     */
    @Mapping(target = "zoneGeographiqueId", source = "zoneGeographique.id")
    @Mapping(target = "zoneGeographique", source = "zoneGeographique")
    @Mapping(target = "departementId", source = "departement.id")
    @Mapping(target = "departement", source = "departement")
    @Mapping(target = "communeId", source = "commune.id")
    @Mapping(target = "villageId", source = "village.id")
    ZoneTouristique toApi(ZoneTouristiqueEntity entity);

    /**
     * Convertit un DTO de création en entité squelette.
     * Le service se charge de résoudre les entités complètes depuis les repositories.
     */
    @Mapping(target = "zoneGeographique.id", source = "zoneGeographiqueId")
    @Mapping(target = "departement.id", source = "departementId")
    @Mapping(target = "commune.id", source = "communeId")
    @Mapping(target = "village.id", source = "villageId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    ZoneTouristiqueEntity toEntity(ZoneTouristiqueCreate dto);

    @Mapping(target = "zoneGeographique.id", source = "zoneGeographiqueId")
    @Mapping(target = "departement.id", source = "departementId")
    @Mapping(target = "commune.id", source = "communeId")
    @Mapping(target = "village.id", source = "villageId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actif", ignore = true)
    ZoneTouristiqueEntity toEntity(ZoneTouristiqueUpdate dto);

    ZoneTouristiqueReference toReference(ZoneTouristiqueEntity entity);

    ZoneGeographiqueReference toZoneGeographiqueReference(ZoneGeographiqueEntity entity);
}



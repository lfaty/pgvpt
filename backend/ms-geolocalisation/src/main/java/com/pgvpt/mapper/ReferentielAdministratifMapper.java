package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.*;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        builder = @Builder(disableBuilder = true)
)
public interface ReferentielAdministratifMapper {

    // ==========================================
    // PAYS MAPPINGS
    // ==========================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PaysEntity toEntityPays(PaysCreate dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityPays(PaysUpdate dto, @MappingTarget PaysEntity entity);

    Pays toDtoPays(PaysEntity entity);
    PaysReference toPaysReference(PaysEntity entity);


    // ==========================================
    // REGION MAPPINGS
    // ==========================================
    @Mapping(target = "paysEntity.id", source = "paysId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zoneLinks", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RegionEntity toEntityRegion(RegionCreate dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paysEntity", ignore = true)
    @Mapping(target = "zoneLinks", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityRegion(RegionUpdate dto, @MappingTarget RegionEntity entity);

    @Mapping(target = "pays", source = "paysEntity")
    Region toDtoRegion(RegionEntity entity);

    @Mapping(target = "paysEntity.id", source = "id")
    @Mapping(target = "codePostal", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "zoneLinks", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RegionEntity toEntityRegion(RegionReference dto);

    RegionReference toRegionReference(RegionEntity entity);


    // ==========================================
    // DEPARTEMENT MAPPINGS
    // ==========================================
    @Mapping(target = "region.id", source = "regionId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DepartementEntity toEntityDept(DepartementCreate dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityDept(DepartementUpdate dto, @MappingTarget DepartementEntity entity);

    @Mapping(target = "region", source = "region")
    Departement toDtoDept(DepartementEntity entity);

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "region", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DepartementEntity toEntityDept(DepartementReference dto);

    DepartementReference toDepartementReference(DepartementEntity entity);


    // ==========================================
    // COMMUNE MAPPINGS
    // ==========================================
    @Mapping(target = "departement.id", source = "departementId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CommuneEntity toEntityCom(CommuneCreate dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departement", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityCom(CommuneUpdate dto, @MappingTarget CommuneEntity entity);

    @Mapping(target = "departement", source = "departement")
    Commune toDtoCom(CommuneEntity entity);

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "departement", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CommuneEntity toEntityCom(CommuneReference dto);

    CommuneReference toCommuneReference(CommuneEntity entity);


    // ==========================================
    // VILLAGE MAPPINGS
    // ==========================================
    @Mapping(target = "commune.id", source = "communeId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    VillageEntity toEntityVil(VillageCreate dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityVil(VillageUpdate dto, @MappingTarget VillageEntity entity);

    @Mapping(target = "commune", source = "commune")
    Village toDtoVil(VillageEntity entity);

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    VillageEntity toEntityVil(VillageReference dto);

    VillageReference toVillageReference(VillageEntity entity);


    // ==========================================
    // QUARTIER MAPPINGS
    // ==========================================
    @Mapping(target = "commune.id", source = "communeId")
    @Mapping(target = "village.id", source = "villageId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    QuartierEntity toEntityQuart(QuartierCreate dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "village", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityQuart(QuartierUpdate dto, @MappingTarget QuartierEntity entity);

    @Mapping(target = "commune", source = "commune")
    @Mapping(target = "village", source = "village")
    Quartier toDtoQuart(QuartierEntity entity);

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "village", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    QuartierEntity toEntityQuart(QuartierReference dto);

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) { return null; }
        return value.atOffset(ZoneOffset.UTC);
    }
}

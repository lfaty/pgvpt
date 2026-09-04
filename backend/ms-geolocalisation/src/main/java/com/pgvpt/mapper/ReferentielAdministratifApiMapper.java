package com.pgvpt.mapper;

import com.dev.pgvpt.api.model.*;
import com.pgvpt.model.*;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ReferentielAdministratifApiMapper {

    Pays toApi(PaysEntity entity);
    @Mapping(target = "id", ignore = true)
    PaysEntity toEntity(PaysCreate dto);
    @Mapping(target = "id", ignore = true)
    PaysEntity toEntity(PaysUpdate dto);


    @Mapping(target = "pays", source = "paysEntity")
    Region toApi(RegionEntity entity);
    @Mapping(target = "paysEntity.id", source = "paysId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zoneLinks", ignore = true)
    RegionEntity toEntity(RegionCreate dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paysEntity", ignore = true)
    @Mapping(target = "zoneLinks", ignore = true)
    RegionEntity toEntity(RegionUpdate dto);
    
    @Mapping(target = "paysEntity.id", source = "id")
    @Mapping(target = "codePostal", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "zoneLinks", ignore = true)
    RegionEntity toEntity(RegionReference dto);

    @Mapping(target = "region", source = "region")
    Departement toApi(DepartementEntity entity);
    
    @Mapping(target = "region.id", source = "regionId")
    @Mapping(target = "id", ignore = true)
    DepartementEntity toEntity(DepartementCreate dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "region", ignore = true)
    DepartementEntity toEntity(DepartementUpdate dto);
    
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "region", ignore = true)
    DepartementEntity toEntity(DepartementReference dto);

    @Mapping(target = "departement", source = "departement")
    Commune toApi(CommuneEntity entity);
    
    @Mapping(target = "departement.id", source = "departementId")
    @Mapping(target = "id", ignore = true)
    CommuneEntity toEntity(CommuneCreate dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departement", ignore = true)
    CommuneEntity toEntity(CommuneUpdate dto);
    
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "departement", ignore = true)
    CommuneEntity toEntity(CommuneReference dto);

    @Mapping(target = "commune", source = "commune")
    Village toApi(VillageEntity entity);
    
    @Mapping(target = "commune.id", source = "communeId")
    @Mapping(target = "id", ignore = true)
    VillageEntity toEntity(VillageCreate dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commune", ignore = true)
    VillageEntity toEntity(VillageUpdate dto);
    
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "commune", ignore = true)
    VillageEntity toEntity(VillageReference dto);

    @Mapping(target = "commune", source = "commune")
    @Mapping(target = "village", source = "village")
    Quartier toApi(QuartierEntity entity);
    
    @Mapping(target = "commune.id", source = "communeId")
    @Mapping(target = "village.id", source = "villageId")
    @Mapping(target = "id", ignore = true)
    QuartierEntity toEntity(QuartierCreate dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "village", ignore = true)
    QuartierEntity toEntity(QuartierUpdate dto);
    
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "commune", ignore = true)
    @Mapping(target = "village", ignore = true)
    QuartierEntity toEntity(QuartierReference dto);

    PaysReference toPaysReference(PaysEntity entity);
    RegionReference toRegionReference(RegionEntity entity);
    DepartementReference toDepartementReference(DepartementEntity entity);
    CommuneReference toCommuneReference(CommuneEntity entity);
    VillageReference toVillageReference(VillageEntity entity);

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) { return null; }
        return value.atOffset(ZoneOffset.UTC);
    }
}

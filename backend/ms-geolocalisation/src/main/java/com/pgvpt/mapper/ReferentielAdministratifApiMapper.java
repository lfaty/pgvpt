package com.pgvpt.mapper;

import com.dev.pgvpt.api.model.*;
import com.pgvpt.model.*;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReferentielAdministratifApiMapper {

    Pays toApi(PaysEntity entity);
    PaysEntity toEntity(PaysCreate dto);
    PaysEntity toEntity(PaysUpdate dto);


    Region toApi(RegionEntity entity);
    RegionEntity toEntity(RegionCreate dto);

    @Mapping(target = "id", source = "id")
    RegionEntity toEntity(RegionReference dto);

    Departement toApi(DepartementEntity entity);
    DepartementEntity toEntity(DepartementCreate dto);
    @Mapping(target = "id", source = "id")
    DepartementEntity toEntity(DepartementReference dto);

    Commune toApi(CommuneEntity entity);
    CommuneEntity toEntity(CommuneCreate dto);
    @Mapping(target = "id", source = "id")
    CommuneEntity toEntity(CommuneReference dto);

    Village toApi(VillageEntity entity);
    VillageEntity toEntity(VillageCreate dto);
    @Mapping(target = "id", source = "id")
    VillageEntity toEntity(VillageReference dto);

//    VillageReference toApi(VillageEntity entity);
//    VillageEntity toEntity(VillageReference api);

    Quartier toApi(QuartierEntity entity);
    QuartierEntity toEntity(QuartierCreate dto);
    @Mapping(target = "id", source = "id")
    QuartierEntity toEntity(QuartierReference dto);

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) { return null; }
        return value.atOffset(ZoneOffset.UTC);
    }
}

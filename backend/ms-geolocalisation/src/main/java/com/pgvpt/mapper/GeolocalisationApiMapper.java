package com.pgvpt.mapper;

import com.pgvpt.dto.GeolocalisationCreate;
import com.pgvpt.dto.Geolocalisation;
import com.pgvpt.dto.GeolocalisationUpdate;
import com.pgvpt.model.GeolocalisationEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface GeolocalisationApiMapper {

    @Mapping(target = "zoneTouristiqueId", source = "zoneTouristique.id")
    @Mapping(target = "villageId", source = "village.id")
    @Mapping(target = "quartierId", source = "quartier.id")
    Geolocalisation toApi(GeolocalisationEntity entity);

    @Mapping(target = "zoneTouristique.id", source = "zoneTouristiqueId")
    @Mapping(target = "village.id", source = "villageId")
    @Mapping(target = "quartier.id", source = "quartierId")
    @Mapping(target = "id", ignore = true)
    GeolocalisationEntity toEntity(GeolocalisationCreate dto);

    @Mapping(target = "zoneTouristique.id", source = "zoneTouristiqueId")
    @Mapping(target = "village.id", source = "villageId")
    @Mapping(target = "quartier.id", source = "quartierId")
    @Mapping(target = "id", ignore = true)
    GeolocalisationEntity toEntity(GeolocalisationUpdate dto);

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.atOffset(ZoneOffset.UTC);
    }
}

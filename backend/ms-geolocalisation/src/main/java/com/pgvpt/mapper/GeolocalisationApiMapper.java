package com.pgvpt.mapper;

import com.dev.pgvpt.api.model.GeolocalisationCreate;
import com.dev.pgvpt.api.model.Geolocalisation;
import com.dev.pgvpt.api.model.GeolocalisationUpdate;
import com.pgvpt.model.GeolocalisationEntity;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GeolocalisationApiMapper {

    Geolocalisation toApi(GeolocalisationEntity entity);

    GeolocalisationEntity toEntity(GeolocalisationCreate dto);

    GeolocalisationEntity toEntity(GeolocalisationUpdate dto);

    default OffsetDateTime map(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        // Choose your preferred ZoneOffset, e.g., UTC
        return value.atOffset(ZoneOffset.UTC);
    }

}

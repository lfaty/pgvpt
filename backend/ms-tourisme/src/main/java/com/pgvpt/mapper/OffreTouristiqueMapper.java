package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.OffreTouristiqueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OffreTouristiqueMapper {

    Offre toApi(OffreTouristiqueEntity entity);

    OffreTouristiqueEntity toEntity(OffreCreate request);

    default java.time.OffsetDateTime map(java.time.Instant value) {
        return value != null ? java.time.OffsetDateTime.ofInstant(value, java.time.ZoneOffset.UTC) : null;
    }

    default java.time.Instant map(java.time.OffsetDateTime value) {
        return value != null ? value.toInstant() : null;
    }
}

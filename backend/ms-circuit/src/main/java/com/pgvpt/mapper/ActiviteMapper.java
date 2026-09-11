package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.ActiviteEntity;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ActiviteMapper {

    Activite toApi(ActiviteEntity entity);

    ActiviteEntity toEntity(ActiviteCreateRequest activiteCreateRequest);

    ActiviteEntity toEntity(ActiviteUpdateRequest request);

    void updateEntity(ActiviteUpdateRequest request, @MappingTarget ActiviteEntity entity);

    // Conversions OffsetDateTime <-> Instant
    default Instant map(OffsetDateTime value) {
        return value != null ? value.toInstant() : null;
    }

    default OffsetDateTime map(Instant value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}

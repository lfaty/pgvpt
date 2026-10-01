package com.pgvpt.mapper;

import com.pgvpt.dto.SessionEvenement;
import com.pgvpt.dto.SessionEvenementCreate;
import com.pgvpt.dto.SessionEvenementUpdate;
import com.pgvpt.model.SessionEvenementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SessionEvenementMapper {

    SessionEvenement toModel(SessionEvenementEntity entity);

    SessionEvenementEntity toEntity(SessionEvenementCreate request);

    void updateEntity(SessionEvenementUpdate request, @MappingTarget SessionEvenementEntity entity);

    // Conversions OffsetDateTime <-> Instant
    default Instant map(OffsetDateTime value) {
        return value != null ? value.toInstant() : null;
    }

    default OffsetDateTime map(Instant value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}

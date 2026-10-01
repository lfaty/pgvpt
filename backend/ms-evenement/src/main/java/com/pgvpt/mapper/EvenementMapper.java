package com.pgvpt.mapper;

import com.pgvpt.dto.Evenement;
import com.pgvpt.dto.EvenementCreate;
import com.pgvpt.dto.EvenementUpdate;
import com.pgvpt.model.EvenementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvenementMapper {

    Evenement toApi(EvenementEntity entity);

    EvenementEntity toEntity(EvenementCreate request);

    void updateEntity(EvenementUpdate request, @MappingTarget EvenementEntity entity);

    // Conversions OffsetDateTime <-> Instant
    default Instant map(OffsetDateTime value) {
        return value != null ? value.toInstant() : null;
    }

    default OffsetDateTime map(Instant value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}
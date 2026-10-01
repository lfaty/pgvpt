package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.CircuitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CircuitMapper {

    Circuit toApi(CircuitEntity entity);

    List<Circuit> toApiList(List<CircuitEntity> entities);

    CircuitEntity toEntity(CircuitCreateRequest request);

    CircuitEntity toEntity(CircuitUpdateRequest request);

    void updateEntity(CircuitUpdateRequest request, @MappingTarget CircuitEntity entity);

    // Conversions OffsetDateTime <-> Instant
    default Instant map(OffsetDateTime value) {
        return value != null ? value.toInstant() : null;
    }

    default OffsetDateTime map(Instant value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}

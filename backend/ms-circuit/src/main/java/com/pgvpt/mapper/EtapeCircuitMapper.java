package com.pgvpt.mapper;

import com.pgvpt.dto.EtapeCircuit;
import com.pgvpt.dto.EtapeCircuitCreateRequest;
import com.pgvpt.dto.EtapeCircuitUpdateRequest;
import com.pgvpt.model.EtapeCircuitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EtapeCircuitMapper {

    @Mapping(source = "circuit.id", target = "circuitId")
    @Mapping(source = "activite.id", target = "activiteId")
    EtapeCircuit toApi(EtapeCircuitEntity entity);

    List<EtapeCircuit> toApiList(List<EtapeCircuitEntity> entities);

    @Mapping(source = "activiteId", target = "activite.id")
    EtapeCircuitEntity toEntity(EtapeCircuitCreateRequest request);

    @Mapping(source = "activiteId", target = "activite.id")
    EtapeCircuitEntity toEntity(EtapeCircuitUpdateRequest request);

    @Mapping(source = "activiteId", target = "activite.id")
    void updateEntity(EtapeCircuitUpdateRequest request, @MappingTarget EtapeCircuitEntity entity);

    // Conversions OffsetDateTime <-> Instant
    default Instant map(OffsetDateTime value) {
        return value != null ? value.toInstant() : null;
    }

    default OffsetDateTime map(Instant value) {
        return value != null ? value.atOffset(ZoneOffset.UTC) : null;
    }
}

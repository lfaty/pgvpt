package com.pgvpt.mapper;

import com.pgvpt.dto.EtapeCircuit;
import com.pgvpt.dto.EtapeCircuitCreateRequest;
import com.pgvpt.dto.EtapeCircuitUpdateRequest;
import com.pgvpt.model.EtapeCircuitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EtapeCircuitMapper {

    EtapeCircuit toApi(EtapeCircuitEntity entity);

    EtapeCircuitEntity toEntity(EtapeCircuitCreateRequest request);

    void updateEntity(EtapeCircuitUpdateRequest request, @MappingTarget EtapeCircuitEntity entity);
}

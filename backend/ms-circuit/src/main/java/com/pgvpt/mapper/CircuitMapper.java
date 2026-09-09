package com.pgvpt.mapper;

import com.pgvpt.dto.*;
import com.pgvpt.model.CircuitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CircuitMapper {

    Circuit toApi(CircuitEntity entity);

    CircuitEntity toEntity(CircuitCreateRequest request);

    void updateEntity(CircuitUpdateRequest request, @MappingTarget CircuitEntity entity);
//    CircuitEntity toEntity(CalculDistanceCircuitResponse calculDistanceCircuitResponse);
//    CircuitEntity toEntity(CalculDureeCircuitResponse calculDureeCircuitResponse);
//    CircuitEntity toEntity(CalculPrixCircuitResponse calculPrixCircuitResponse);

}

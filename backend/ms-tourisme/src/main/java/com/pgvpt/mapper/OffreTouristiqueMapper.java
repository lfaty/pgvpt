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

   // void updateEntity(OffreUpdate request, @MappingTarget OffreTouristiqueEntity entity);
}

package com.pgvpt.mapper;

import com.pgvpt.dto.ItineraireOptimise;
import com.pgvpt.dto.OptimisationItineraireRequest;
import com.pgvpt.viewModel.ItineraireOptimiseViewModel;
import com.pgvpt.viewModel.OptimisationItineraireRequestViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItineraireOptimizationMapper {

    OptimisationItineraireRequestViewModel toViewModel(OptimisationItineraireRequest request);

    ItineraireOptimise toModel(ItineraireOptimiseViewModel viewModel);
}
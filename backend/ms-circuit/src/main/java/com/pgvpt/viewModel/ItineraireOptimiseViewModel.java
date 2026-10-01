package com.pgvpt.viewModel;

import com.pgvpt.enums.MethodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraireOptimiseViewModel {

    private UUID circuitId;

    private MethodeEnum methode;

    private Double distanceTotaleKm;

    private Integer dureeTotaleMinutes;

    private Double coutTotal;

    private Double scoreOptimisation;

    private List<EtapeOptimiseeViewModel> etapes;
}

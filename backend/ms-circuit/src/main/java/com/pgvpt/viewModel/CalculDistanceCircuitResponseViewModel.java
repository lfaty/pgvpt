package com.pgvpt.viewModel;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculDistanceCircuitResponseViewModel {
    private UUID circuitId;

    private Double distanceTotaleKm;

    private OffsetDateTime calculeLe;

}


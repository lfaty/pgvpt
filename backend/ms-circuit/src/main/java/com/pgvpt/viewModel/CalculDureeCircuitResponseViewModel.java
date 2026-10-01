package com.pgvpt.viewModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculDureeCircuitResponseViewModel {
    private UUID circuitId;

    private Integer dureeTotaleMinutes;

    private OffsetDateTime calculeLe;
}

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
public class CalculPrixCircuitResponseViewModel {
    private UUID circuitId;

    private Double prixTotal;

    private String devise = "XOF";

    private OffsetDateTime calculeLe;
}

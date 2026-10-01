package com.pgvpt.viewModel;

import com.pgvpt.model.EtapeCircuitEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtapeAvecCoordonneesViewModel {

    private EtapeCircuitEntity etape;

    private double latitude;

    private double longitude;
}


package com.pgvpt.viewModel;

import com.pgvpt.model.ActiviteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtapeOptimiseeViewModel {

    private Integer ordre;

    private UUID patrimoineId;

    private ActiviteEntity activite;

    private Double latitude;

    private Double longitude;

    private Double distanceDepuisPrecedenteKm;

    private Integer dureeMinutes;

    private Double cout;
}

package com.pgvpt.viewModel;

import com.pgvpt.enums.MethodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimisationItineraireRequestViewModel {

    private MethodeEnum methode = MethodeEnum.MULTICRITERE;
    //private MethodeEnum methode;

    private Double latitudeDepart;

    private Double longitudeDepart;

    private Double latitudeArrivee;

    private Double longitudeArrivee;

    private Integer dureeMaxMinutes;

    private Double poidsDistance = 0.5d;

    private Double poidsDuree = 0.3d;

    private Double poidsCout = 0.2d;

}

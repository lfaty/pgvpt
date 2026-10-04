package com.pgvpt.record;

import com.pgvpt.dto.CategoriePatrimoine;
import com.pgvpt.dto.TypePatrimoine;

import java.util.List;

public record GeolocalisationSearchCriteria(Double latitude,
                                            Double longitude,
                                            Double rayonKm,
                                            Double minLatitude,
                                            Double maxLatitude,
                                            Double minLongitude,
                                            Double maxLongitude,
                                            String localite,
                                            List<String> motCle,
                                            TypePatrimoine typePatrimoine,
                                            CategoriePatrimoine categorie) {
}

package com.pgvpt.client;

import com.pgvpt.record.GeolocalisationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-geolocalisation", path = "/api/v1/geolocalisations"
)
public interface GeolocalisationClient {

    @GetMapping("/patrimoine/{patrimoineId}")
    GeolocalisationResponse getByPatrimoine(@PathVariable UUID patrimoineId);
}

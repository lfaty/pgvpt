package com.pgvpt.client;

import com.pgvpt.record.GeolocalisationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-geolocalisation", contextId = "geolocalisationClient")
public interface GeolocalisationClient {

    @GetMapping("/api/v1/geolocalisations/patrimoines/{patrimoineId}/geolocalisation")
    GeolocalisationResponse getByPatrimoine(@PathVariable("patrimoineId") UUID patrimoineId);
}

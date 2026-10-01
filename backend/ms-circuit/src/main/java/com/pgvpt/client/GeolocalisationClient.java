package com.pgvpt.client;

import com.pgvpt.record.GeolocalisationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-geolocalisation", url = "http://localhost:3002", contextId = "GeolocalisationClient")
public interface GeolocalisationClient {

    @GetMapping("/patrimoines/{patrimoineId}/geolocalisation")
    GeolocalisationResponse getByPatrimoine(@PathVariable("patrimoineId") UUID patrimoineId);
}

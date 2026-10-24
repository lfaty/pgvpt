package com.pgvpt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
    name = "ms-geolocalisation", 
    contextId = "zoneTouristiqueClient", 
    path = "/api/v1/geolocalisations/zones-touristiques"
)
public interface ZoneTouristiqueClient {

    @GetMapping("/{id}")
    Object getById(@PathVariable("id") UUID id);
}

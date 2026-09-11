package com.pgvpt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-geolocalisation", contextId = "zoneTouristiqueClient", url = "http://localhost:3002", path = "/zones-touristiques")
public interface ZoneTouristiqueClient {

    @GetMapping("/{id}")
    Object getById(@PathVariable UUID id);
}

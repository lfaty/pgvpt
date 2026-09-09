package com.pgvpt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-geolocalisation", path = "/api/v1/zones-touristiques"
)
public interface ZoneTouristiqueClient {

//    @GetMapping("/{id}")
//    ZoneTouristiqueResponse getById(@PathVariable UUID id);
}
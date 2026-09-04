package com.pgvpt.client;

import com.pgvpt.dto.GeolocalisationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "ms-geolocalisation", url = "http://localhost:3002")
public interface GeolocalisationClient {

    @PostMapping("/geolocalisations")
    GeolocalisationDTO createGeolocalisation(@RequestBody GeolocalisationDTO geolocalisationDTO);

    @PutMapping("/patrimoines/{patrimoineId}/geolocalisation")
    GeolocalisationDTO updatePatrimoineGeolocalisation(@PathVariable("patrimoineId") UUID patrimoineId, @RequestBody GeolocalisationDTO geolocalisationDTO);

    @GetMapping("/patrimoines/{patrimoineId}/geolocalisation")
    GeolocalisationDTO getPatrimoineGeolocalisation(@PathVariable("patrimoineId") UUID patrimoineId);
}

package com.pgvpt.media.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-patrimoine")
public interface PatrimoineClient {

    @GetMapping("/api/v1/patrimoines/{id}")
    Object getById(@PathVariable("id") UUID id);
}

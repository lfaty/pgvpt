package com.pgvpt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-patrimoine", url = "http://localhost:3001", contextId = "PatrimoineClient")
public interface PatrimoineClient {

    @GetMapping("/api/v1/patrimoines/{id}")
    Object getPatrimoineById(@PathVariable("id") UUID id);
}

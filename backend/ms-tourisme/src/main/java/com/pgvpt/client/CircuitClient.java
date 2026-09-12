package com.pgvpt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-circuit", url = "http://localhost:3006", path = "/api/v1/circuits")
public interface CircuitClient {

    @GetMapping("/{id}")
    Object getById(@PathVariable UUID id);
}

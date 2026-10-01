package com.pgvpt.media.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-patrimoine", url = "http://localhost:3001/api/v1")
public interface PatrimoineClient {

    @GetMapping("/patrimoines/{id}")
    Object getById(@PathVariable("id") UUID id);
}

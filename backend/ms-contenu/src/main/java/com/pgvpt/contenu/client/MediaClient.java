package com.pgvpt.contenu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-media")
public interface MediaClient {

    @GetMapping("/api/v1/medias/{id}")
    Object getById(@PathVariable("id") UUID id);
}

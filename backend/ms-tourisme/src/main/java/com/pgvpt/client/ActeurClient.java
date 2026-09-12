package com.pgvpt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

// @FeignClient(name = "ms-acteur", path = "/api/v1/acteurs")
// public interface ActeurClient {
//
// //    @GetMapping("/{id}")
// //    ActeurResponse getById(@PathVariable UUID id);
// }

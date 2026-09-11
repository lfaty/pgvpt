package com.pgvpt.contenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsContenuApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsContenuApplication.class, args);
    }
}

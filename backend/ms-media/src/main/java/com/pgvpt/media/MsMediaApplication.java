package com.pgvpt.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsMediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsMediaApplication.class, args);
    }
}

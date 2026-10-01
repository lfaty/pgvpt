package com.pgvpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPatrimoineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPatrimoineApplication.class, args);
	}

}

package com.pgvpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PgvptDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgvptDiscoveryApplication.class, args);
	}

}

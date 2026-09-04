package com.pgvpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MsGeolocalisationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGeolocalisationApplication.class, args);
	}

}

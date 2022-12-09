package com.influx.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.influx.database.entity")
@EnableJpaRepositories("com.influx.database.repository")
public class InfluxEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfluxEngineApplication.class, args);
	}

}

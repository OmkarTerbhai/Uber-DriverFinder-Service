package com.uber.driverfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DriverfinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverfinderApplication.class, args);
	}

}

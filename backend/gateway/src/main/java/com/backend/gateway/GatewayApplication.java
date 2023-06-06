package com.backend.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GatewayApplication.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		app.run(args);
	}

}

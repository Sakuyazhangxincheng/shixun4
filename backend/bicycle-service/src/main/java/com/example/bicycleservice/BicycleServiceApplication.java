package com.example.bicycleservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.example.bicycleservice.mapper")
public class BicycleServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BicycleServiceApplication.class, args);
    }
}

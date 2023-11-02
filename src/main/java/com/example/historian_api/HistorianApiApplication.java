package com.example.historian_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@OpenAPIDefinition(servers =
        {
                @Server(
                        url = "https://historian-api.up.railway.app",
                        description = "Remote Project for Historian API"
                ),
                @Server(
                        url = "http://localhost:8000",
                        description = "Local Project for Historian API"
                )
        }
)
@CrossOrigin(
        origins = "*",
        maxAge = 3600,
        allowedHeaders = "*")
public class HistorianApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistorianApiApplication.class, args);
    }

}

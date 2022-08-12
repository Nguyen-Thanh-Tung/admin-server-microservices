package com.comit.services.history.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "History API", version = "1.0", description = "Documentation History API v1.0")
)
public class HistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryApplication.class, args);
    }
}

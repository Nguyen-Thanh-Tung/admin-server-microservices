package com.example.userlog.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Location API", version = "1.0", description = "Documentation Location API v1.0")
)
public class UserLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserLogApplication.class, args);
    }
}

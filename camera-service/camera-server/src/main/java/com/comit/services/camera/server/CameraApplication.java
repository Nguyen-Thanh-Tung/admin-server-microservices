package com.comit.services.camera.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Camera API", version = "1.0", description = "Documentation Camera API v1.0")
)
public class CameraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CameraApplication.class, args);
    }
}

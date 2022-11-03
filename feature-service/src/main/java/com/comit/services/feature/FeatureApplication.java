package com.comit.services.feature;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info =
@Info(title = "Feature API", version = "1.0", description = "Documentation Feature API v1.0")
)
public class FeatureApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureApplication.class, args);
    }

}

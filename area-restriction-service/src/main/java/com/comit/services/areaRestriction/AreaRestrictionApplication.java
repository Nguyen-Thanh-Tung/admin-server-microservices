package com.comit.services.areaRestriction;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info =
@Info(title = "Area Restriction API", version = "1.0", description = "Documentation Area Restriction API v1.0")
)
public class AreaRestrictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AreaRestrictionApplication.class, args);
    }

}

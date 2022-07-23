package com.comit.services.timeKeeping;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info =
@Info(title = "Time Keeping API", version = "1.0", description = "Documentation Time Keeping API v1.0")
)
public class TimeKeepingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeKeepingApplication.class, args);
    }

}

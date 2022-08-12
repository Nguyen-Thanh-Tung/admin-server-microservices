package com.comit.services.mail.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Mail API", version = "1.0", description = "Documentation Mail API v1.0")
)
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

}

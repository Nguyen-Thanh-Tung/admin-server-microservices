package com.comit.services.organization.server;

import com.comit.services.organization.server.repository.OrganizationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrganizationServerApplicationTests {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrganizationRepository organizationRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
//        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getReplicaSetUrl);
    }
    @Test
    void contextLoads() {
    }

}

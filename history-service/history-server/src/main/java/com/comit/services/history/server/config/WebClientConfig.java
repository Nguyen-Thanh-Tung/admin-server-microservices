package com.comit.services.history.server.config;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.AccountClientImpl;
import com.comit.services.areaRestriction.client.AreaRestrictionClient;
import com.comit.services.areaRestriction.client.AreaRestrictionClientImpl;
import com.comit.services.camera.client.CameraClient;
import com.comit.services.camera.client.CameraClientImpl;
import com.comit.services.employee.client.EmployeeClient;
import com.comit.services.employee.client.EmployeeClientImpl;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.LocationClientImpl;
import com.comit.services.metadata.client.MetadataClient;
import com.comit.services.metadata.client.MetadataClientImpl;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public MetadataClient getMetadataClient() {
        return new MetadataClientImpl(webClientBuilder());
    }

    @Bean
    public AccountClient getAccountClient() {
        return new AccountClientImpl(webClientBuilder());
    }

    @Bean
    public LocationClient getLocationClient() {
        return new LocationClientImpl(webClientBuilder());
    }

    @Bean
    public CameraClient getCameraClient() {
        return new CameraClientImpl(webClientBuilder());
    }

    @Bean
    public AreaRestrictionClient getAreaRestrictionClient() {
        return new AreaRestrictionClientImpl(webClientBuilder());
    }

    @Bean
    public EmployeeClient getEmployeeClient() {
        return new EmployeeClientImpl(webClientBuilder());
    }
}

package com.comit.services.camera.server.config;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.AccountClientImpl;
import com.comit.services.areaRestriction.client.AreaRestrictionClient;
import com.comit.services.areaRestriction.client.AreaRestrictionClientImpl;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.LocationClientImpl;
import com.comit.services.organization.client.OrganizationClient;
import com.comit.services.organization.client.OrganizationClientImpl;
import com.comit.services.userlog.client.UserLogClient;
import com.comit.services.userlog.client.UserLogClientImpl;
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
    public LocationClient getLocationClient() {
        return new LocationClientImpl(webClientBuilder());
    }


    @Bean
    public AccountClient getAccountClient() {
        return new AccountClientImpl(webClientBuilder());
    }

    @Bean
    public OrganizationClient getOrganizationClient() {
        return new OrganizationClientImpl(webClientBuilder());
    }

    @Bean
    public UserLogClient getUserLogClient() {
        return new UserLogClientImpl(webClientBuilder());
    }

    @Bean
    public AreaRestrictionClient getAreaRestrictionClient() {
        return new AreaRestrictionClientImpl(webClientBuilder());
    }
}

package com.comit.services.organization.server.config;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.AccountClientImpl;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.LocationClientImpl;
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
    public AccountClient getAccountClient() {
        return new AccountClientImpl(webClientBuilder());
    }

    @Bean
    public LocationClient getLocationClient() {
        return new LocationClientImpl(webClientBuilder());
    }

    @Bean
    public UserLogClient getUserLogClient() {
        return new UserLogClientImpl(webClientBuilder());
    }
}
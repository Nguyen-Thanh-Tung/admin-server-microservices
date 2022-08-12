package com.comit.services.account.server.config;

import com.comit.services.feature.client.FeatureClient;
import com.comit.services.feature.client.FeatureClientImpl;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.LocationClientImpl;
import com.comit.services.mail.client.MailClient;
import com.comit.services.mail.client.MailClientImpl;
import com.comit.services.metadata.client.MetadataClient;
import com.comit.services.metadata.client.MetadataClientImpl;
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
    public OrganizationClient getOrganizationClient() {
        return new OrganizationClientImpl(webClientBuilder());
    }

    @Bean
    public FeatureClient getFeatureClient() {
        return new FeatureClientImpl(webClientBuilder());
    }

    @Bean
    public MetadataClient getMetadataClient() {
        return new MetadataClientImpl(webClientBuilder());
    }

    @Bean
    public MailClient getMailClient() {
        return new MailClientImpl(webClientBuilder());
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

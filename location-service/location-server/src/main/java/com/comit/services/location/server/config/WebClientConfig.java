package com.comit.services.location.server.config;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.AccountClientImpl;
import com.comit.services.camera.client.CameraClient;
import com.comit.services.camera.client.CameraClientImpl;
import com.comit.services.employee.client.EmployeeClient;
import com.comit.services.employee.client.EmployeeClientImpl;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.LocationClientImpl;
import com.comit.services.organization.client.OrganizationClient;
import com.comit.services.organization.client.OrganizationClientImpl;
import com.comit.services.timeKeeping.client.TimeKeepingClient;
import com.comit.services.timeKeeping.client.TimeKeepingClientImpl;
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
    public OrganizationClient getOrganizationClient() {
        return new OrganizationClientImpl(webClientBuilder());
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
    public EmployeeClient getEmployeeClient() {
        return new EmployeeClientImpl(webClientBuilder());
    }

    @Bean
    public TimeKeepingClient getTimeKeepingClient() {
        return new TimeKeepingClientImpl(webClientBuilder());
    }

    @Bean
    public UserLogClient getUserLogClient() {
        return new UserLogClientImpl(webClientBuilder());
    }
}

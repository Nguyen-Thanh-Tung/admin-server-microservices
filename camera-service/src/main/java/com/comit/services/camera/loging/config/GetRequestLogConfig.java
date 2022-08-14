package com.comit.services.camera.loging.config;

import com.comit.services.camera.loging.adapter.GetRequestLogAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Add interceptor
 */
@Configuration
public class GetRequestLogConfig implements WebMvcConfigurer {

    @Bean
    public GetRequestLogAdapter getRequestLogAdapter() {
        return new GetRequestLogAdapter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Get Request log order 1 (after get request (order 0) to make sure add HEADER_REQUEST_ID done)
        registry.addInterceptor(getRequestLogAdapter()).order(1);
    }
}

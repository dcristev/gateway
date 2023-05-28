package com.examplatform.gatewayproject.config;

import com.examplatform.gatewayproject.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        Map<String, String> getenv = System.getenv();

        return builder.routes()
                .route("exam-creation-service", r -> r.path("/exam/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8082"))

                .route("auth-service", r -> r.path("/exam/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8080"))

                .route("exam-submit-service", r -> r.path("/exam/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083"))
                .build();
    }
}

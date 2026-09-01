package com.liveinterviewer.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Every route below uses the lb:// scheme, meaning "ask Eureka for the
// current address of this service, then forward the request there."
// Service names here MUST exactly match each service's spring.application.name.
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("lb://auth_db"))
                .route("user-service", r -> r.path("/api/users/**")
                        .uri("lb://user-service"))
                .route("session-service", r -> r.path("/api/sessions/**")
                        .uri("lb://session-service"))
                .route("scorecard-service", r -> r.path("/api/scorecards/**")
                        .uri("lb://scorecard-service"))
                .route("ai-gateway-service", r -> r.path("/api/ai/**")
                        .uri("lb://ai-gateway-service"))
                // WebSocket traffic gets the lb:ws:// scheme instead of lb://
                .route("session-websocket", r -> r.path("/ws/**")
                        .uri("lb:ws://session-service"))
                .build();
    }
}

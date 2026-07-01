package andrey.dev.apigateway.config;

import andrey.dev.apigateway.security.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_public", r -> r
                        .path("/api/v1/auth/login",
                                "/api/v1/auth/admin")
                        .uri("http://auth-service-app:8081"))

                .route("auth_protected", r -> r
                        .path("/api/v1/auth/**")
                        .filters(f -> f
                                .filter(authenticationFilter))
                        .uri("http://auth-service-app:8081"))

                .route("user_service", r -> r
                        .path("/api/v1/users/**", "/api/v1/payment-cards/**")
                        .filters(f -> f
                                .filter(authenticationFilter))
                        .uri("http://user-service-app:8080"))

                .route("order_service", r -> r
                        .path("/api/v1/orders/**", "/api/v1/items/**")
                        .filters(f -> f
                                .filter(authenticationFilter))
                        .uri("http://order-service-app:8083"))

                .route("gateway_register", r -> r
                        .path("/api/v1/gateway/**")
                        .uri("http://localhost:8085"))

                .build();
    }
}

package andrey.dev.apigateway.service;

import andrey.dev.apigateway.dto.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient webClient;

    private static final String AUTH_SERVICE_URL = "http://auth-service-app:8081";

    public Mono<String> registerUser(RegisterRequest request) {
        return webClient.post()
                .uri(AUTH_SERVICE_URL + "/api/v1/auth/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<JwtAuthenticationDto> login(UserCredentialsDto credentials) {
        return webClient.post()
                .uri(AUTH_SERVICE_URL + "/api/v1/auth/login")
                .bodyValue(credentials)
                .retrieve()
                .bodyToMono(JwtAuthenticationDto.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<JwtAuthenticationDto> refreshToken(RefreshTokenDto request) {
        return webClient.post()
                .uri(AUTH_SERVICE_URL + "/api/v1/auth/token")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(JwtAuthenticationDto.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<ValidationResponse> validateToken(TokenRequest request) {
        return webClient.post()
                .uri(AUTH_SERVICE_URL + "/api/v1/auth/validate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ValidationResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Page<AuthUserDto>> getAllUsers(Pageable pageable) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(AUTH_SERVICE_URL + "/api/v1/auth")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .queryParam("sort", pageable.getSort().toString())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Page<AuthUserDto>>() {
                })
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> deleteUser(String login) {
        return webClient.delete()
                .uri(AUTH_SERVICE_URL + "/api/v1/auth/{login}", login)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> registerAdmin(RegisterRequest request) {
        return webClient.post()
                .uri(AUTH_SERVICE_URL + "/api/v1/auth/admin")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }


}

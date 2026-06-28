package andrey.dev.apigateway.service;

import andrey.dev.apigateway.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    private static final String USER_SERVICE_URL = "http://user-service-app:8080";

    public Mono<UserResponse> getUserById(Long id) {
        return webClient.get()
                .uri(USER_SERVICE_URL + "/api/v1/users/{id}", id)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Page<UserResponse>> getAllUsers(Pageable pageable, String firstName, String surName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(USER_SERVICE_URL + "/api/v1/users")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .queryParam("sort", pageable.getSort().toString())
                        .queryParam("firstName", firstName)
                        .queryParam("surName", surName)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Page<UserResponse>>() {
                })
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return webClient.post()
                .uri(USER_SERVICE_URL + "/api/v1/users")
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> updateUser(Long id, UserRequest userRequest) {
        return webClient.patch()
                .uri(USER_SERVICE_URL + "/api/v1/users/{id}", id)
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> activateUser(Long id) {
        return webClient.patch()
                .uri(USER_SERVICE_URL + "/api/v1/users/activate/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> deactivateUser(Long id) {
        return webClient.patch()
                .uri(USER_SERVICE_URL + "/api/v1/users/deactivate/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> deleteUser(Long id) {
        return webClient.delete()
                .uri(USER_SERVICE_URL + "/api/v1/users/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Page<PaymentCardResponse>> getAllPaymentCards(Pageable pageable) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(USER_SERVICE_URL + "/api/v1/payment-cards")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .queryParam("sort", pageable.getSort().toString())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Page<PaymentCardResponse>>() {
                })
                .timeout(Duration.ofSeconds(5));
    }


    public Mono<Void> deletePaymentCard(Long id) {
        return webClient.delete()
                .uri(USER_SERVICE_URL + "/api/v1/payment-cards/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<PaymentCardResponse> createPaymentCard(PaymentCardRequest paymentCardRequest) {
        return webClient.post()
                .uri(USER_SERVICE_URL + "/api/v1/payment-cards")
                .bodyValue(paymentCardRequest)
                .retrieve()
                .bodyToMono(PaymentCardResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> updatePaymentCardById(PaymentCardRequest userRequest, Long id) {
        return webClient.patch()
                .uri(USER_SERVICE_URL + "/api/v1/payment-cards/{id}", id)
                .bodyValue(userRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> activatePaymentCard(Long id) {
        return webClient.patch()
                .uri(USER_SERVICE_URL + "/api/v1/payment-cards/activate/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> deactivatePaymentCard(Long id) {
        return webClient.patch()
                .uri(USER_SERVICE_URL + "/api/v1/payment-cards/deactivate/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<PaymentCardResponse> getPaymentCardById(Long id) {
        return webClient.get()
                .uri(USER_SERVICE_URL + "/api/v1/payment-cards/{id}", id)
                .retrieve()
                .bodyToMono(PaymentCardResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<List<PaymentCardResponse>> getPaymentCardsByUserId(Long userId) {
        return webClient.get()
                .uri(USER_SERVICE_URL + "/api/v1/payment-cards/user/{user-id}", userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PaymentCardResponse>>() {
                })
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<TempUserResponse> createTempUser(TempUserRequest tempUserRequest) {
        return webClient.post()
                .uri(USER_SERVICE_URL + "/api/v1/users/temp")
                .bodyValue(tempUserRequest)
                .retrieve()
                .bodyToMono(TempUserResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<UserResponse> createUserFromTemp(String email) {
        return webClient.post()
                .uri(USER_SERVICE_URL + "/api/v1/users/temp/{email}", email)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .timeout(Duration.ofSeconds(5));
    }

    public Mono<Void> deleteTempUser(String email) {
        String url = USER_SERVICE_URL + "/api/v1/users/temp?email=" + email;

        return webClient.delete()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5))
                .doOnSuccess(v -> log.info("Temp user deleted: {}", email))
                .doOnError(error -> log.error(" Failed to delete temp user: {}", email, error));
    }


}

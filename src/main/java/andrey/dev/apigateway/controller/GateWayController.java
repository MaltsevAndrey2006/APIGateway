package andrey.dev.apigateway.controller;

import andrey.dev.apigateway.dto.RegistrationRequest;
import andrey.dev.apigateway.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/gateway")
public class GateWayController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody RegistrationRequest registrationRequest) {
        return registrationService.registerUser(registrationRequest)
                .then(Mono.just(ResponseEntity.ok("User registered successfully")))
                .onErrorResume(error ->
                        Mono.just(ResponseEntity.badRequest().body("Error: " + error.getMessage()))
                );
    }

}

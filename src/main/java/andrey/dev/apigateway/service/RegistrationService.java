package andrey.dev.apigateway.service;

import andrey.dev.apigateway.dto.RegistrationRequest;
import andrey.dev.apigateway.dto.auth.RegisterRequest;
import andrey.dev.apigateway.dto.user.TempUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final AuthServiceClient authServiceClient;
    private final UserServiceClient userServiceClient;


    public Mono<Void> registerUser(RegistrationRequest request) {

        TempUserRequest tempUserRequest = new TempUserRequest();

        tempUserRequest.setName(request.getName());
        tempUserRequest.setSurname(request.getSurname());
        tempUserRequest.setEmail(request.getEmail());
        tempUserRequest.setBirthDate(request.getBirthDate());

        RegisterRequest registerRequest = new RegisterRequest();

        System.out.println(request);

        registerRequest.setLogin(request.getLogin());
        registerRequest.setPassword(request.getPassword());

        return userServiceClient.createTempUser(tempUserRequest)
                .flatMap(tempUser -> {

                    String tempEmail = tempUser.getEmail();

                    return authServiceClient.registerUser(registerRequest)
                            .then(Mono.defer(() -> userServiceClient.createUserFromTemp(tempEmail)))
                            .onErrorResume(error -> {
                                System.out.println("ORIGINAL ERROR:");
                                error.printStackTrace();

                                return userServiceClient.deleteTempUser(tempEmail)
                                        .then(Mono.error(error));
                            });
                }).then();
    }

}

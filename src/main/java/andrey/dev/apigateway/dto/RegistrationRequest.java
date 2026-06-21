package andrey.dev.apigateway.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationRequest {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 100)
    private String surname;

    @NotNull
    @Past
    private LocalDate birthDate;

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}

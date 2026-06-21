package andrey.dev.apigateway.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCredentialsDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}

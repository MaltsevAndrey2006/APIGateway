package andrey.dev.apigateway.dto.auth;

import andrey.dev.apigateway.enums.Role;
import lombok.Data;


@Data
public class AuthUserDto {
    private Long id;

    private String login;

    private Role role;
}

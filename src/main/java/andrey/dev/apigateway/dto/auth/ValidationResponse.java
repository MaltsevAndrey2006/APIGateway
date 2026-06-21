package andrey.dev.apigateway.dto.auth;

import andrey.dev.apigateway.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResponse {
    private boolean valid;
    private Role role;
    private Long id;
}

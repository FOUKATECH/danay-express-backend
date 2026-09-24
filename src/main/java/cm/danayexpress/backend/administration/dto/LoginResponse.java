package cm.danayexpress.backend.administration.dto;

import java.util.Set;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresInSeconds,
        UserResponse user,
        Set<String> permissions
) {
}

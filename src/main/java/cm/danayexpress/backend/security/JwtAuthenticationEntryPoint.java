package cm.danayexpress.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String jsonResponse = String.format(
                "{\"timestamp\":\"%s\",\"status\":401,\"error\":\"Non autorisé\",\"message\":\"%s\"}",
                Instant.now().toString(),
                authException.getMessage() != null ? authException.getMessage() : "Accès refusé - Token d'authentification manquant ou invalide"
        );

        response.getWriter().write(jsonResponse);
    }
}

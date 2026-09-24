package cm.danayexpress.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long jwtExpirationInMs;

    public JwtTokenProvider(
            @Value("${app.jwt.secret:changeme-en-production-avec-une-vraie-cle-secrete-32-octets-minimum-123456}") String secret,
            @Value("${app.jwt.expiration-minutes:480}") long expirationMinutes) {
        
        // S'assurer d'avoir au moins 256 bits (32 octets) pour HMAC-SHA256
        String paddedSecret = secret;
        if (paddedSecret.length() < 32) {
            paddedSecret = String.format("%-32s", secret).replace(' ', '0');
        }
        this.key = Keys.hmacShaKeyFor(paddedSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = expirationMinutes * 60 * 1000;
    }

    public String generateToken(String username, String roleCode) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .claim("role", roleCode)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.error("Token JWT invalide ou expiré : {}", ex.getMessage());
            return false;
        }
    }

    public long getExpirationInSeconds() {
        return jwtExpirationInMs / 1000;
    }
}

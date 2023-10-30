package br.com.solutis.squad1.identityservice.service;

import br.com.solutis.squad1.identityservice.exception.UnauthorizedException;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.jwt.secret}")
    private String secret;

    @Value("${api.security.provider}")
    private String provider;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(provider)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("authorities", user.getAuthorities().toString())
                    .withExpiresAt(generateExpiresAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating token", exception);
        }
    }

    public String verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(provider)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    private Instant generateExpiresAt() {
        return LocalDateTime.now().plusHours(168).toInstant(ZoneOffset.UTC);
    }
}

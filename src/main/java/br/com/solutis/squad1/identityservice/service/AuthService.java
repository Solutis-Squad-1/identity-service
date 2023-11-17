package br.com.solutis.squad1.identityservice.service;

import br.com.solutis.squad1.identityservice.dto.TokenDto;
import br.com.solutis.squad1.identityservice.dto.user.UserLoginDto;
import br.com.solutis.squad1.identityservice.exception.UnauthorizedException;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import br.com.solutis.squad1.identityservice.model.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Value("${api.security.token.jwt.secret}")
    private String secret;

    @Value("${api.security.provider}")
    private String provider;

    public TokenDto login(UserLoginDto userLoginDTO) {
        log.info("Authenticating user {}", userLoginDTO.username());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.username(),
                userLoginDTO.password()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String tokenJwt = generateToken((User) authentication.getPrincipal());
        return new TokenDto(tokenJwt);
    }

    public String verifyToken(String token) {
        log.info("Verifying token {}", token);
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

    private String generateToken(User user) {
        log.info("Generating token for user {}", user.getUsername());
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(provider)
                    .withSubject(user.getUsername())
                    .withClaim("authorities", user.getAuthorities().toString())
                    .withExpiresAt(generateExpiresAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error creating token", exception);
        }
    }

    private Instant generateExpiresAt() {
        log.info("Generating expiration date");
        return LocalDateTime.now().plusHours(168).toInstant(ZoneOffset.UTC);
    }

    public TokenDto loginByUsername(String username) {
        log.info("Authenticating user {}", username);

        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String tokenJwt = generateToken(user);
        return new TokenDto(tokenJwt);
    }
}

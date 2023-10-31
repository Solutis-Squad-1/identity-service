package br.com.solutis.squad1.identityservice.controller;

import br.com.solutis.squad1.identityservice.dto.TokenDto;
import br.com.solutis.squad1.identityservice.dto.user.UserLoginDto;
import br.com.solutis.squad1.identityservice.dto.user.UserRegisterDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.exception.UnauthorizedException;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import br.com.solutis.squad1.identityservice.service.TokenService;
import br.com.solutis.squad1.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identity/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public TokenDto authenticate(
            @RequestBody @Valid UserLoginDto userLoginDTO
    ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.username(),
                userLoginDTO.password()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String tokenJwt = tokenService.generateToken((User) authentication.getPrincipal());
        return new TokenDto(tokenJwt);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(
            @RequestBody @Valid UserRegisterDto userRegisterDTO
    ) {
        return userService.save(userRegisterDTO);
    }

    @PostMapping("/validate")
    public TokenDto validate(
            @RequestBody @Valid TokenDto tokenDTO
    ) {
        tokenService.verifyToken(tokenDTO.token());
        return tokenDTO;
    }
}
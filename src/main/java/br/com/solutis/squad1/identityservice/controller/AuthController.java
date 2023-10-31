package br.com.solutis.squad1.identityservice.controller;

import br.com.solutis.squad1.identityservice.dto.TokenDto;
import br.com.solutis.squad1.identityservice.dto.user.UserLoginDto;
import br.com.solutis.squad1.identityservice.dto.user.UserRegisterDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.service.AuthService;
import br.com.solutis.squad1.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identity/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public TokenDto authenticate(
            @RequestBody @Valid UserLoginDto userLoginDTO
    ) {
        TokenDto token = authService.login(userLoginDTO);
        userService.updateConfirmedByName(userLoginDTO.username(), false);
        userService.demoteRoleByName(userLoginDTO.username());
        return token;
        // TODO enviar o codigo de confirmacao de 2 fatores por email
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
        authService.verifyToken(tokenDTO.token());
        return tokenDTO;
    }

    // TODO Endpoint para confirmar o codigo de 2 fatores
}
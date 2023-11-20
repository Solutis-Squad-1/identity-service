package br.com.solutis.squad1.identityservice.controller;

import br.com.solutis.squad1.identityservice.dto.TokenDto;
import br.com.solutis.squad1.identityservice.dto.user.UserLoginDto;
import br.com.solutis.squad1.identityservice.dto.user.UserRegisterDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.producer.EmailNotificationProducer;
import br.com.solutis.squad1.identityservice.service.AuthService;
import br.com.solutis.squad1.identityservice.service.OtpService;
import br.com.solutis.squad1.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/identity/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final OtpService otpService;
    private final EmailNotificationProducer emailNotificationProducer;

    /**
     * Login user, demote role and send email with otp
     * @param userLoginDTO
     * @return TokenDto
     */
    @PostMapping("/login")
    public TokenDto login(
            @RequestBody @Valid UserLoginDto userLoginDTO
    ) {
        TokenDto token = authService.login(userLoginDTO);

        userService.updateConfirmedByName(userLoginDTO.username(), false);
        UserResponseDto userResponseDto = userService.demoteRoleByName(userLoginDTO.username());

        String code = otpService.create(userResponseDto);
        emailNotificationProducer.sendOtp(userResponseDto, code);

        return token;
    }

    /**
     * Register user
     * @param userRegisterDTO
     * @return UserResponseDto
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(
            @RequestBody @Valid UserRegisterDto userRegisterDTO
    ) {
        return userService.save(userRegisterDTO);
    }

    /**
     * Validate token. Return same token if valid, return 401 if invalid
     * @param tokenDTO
     * @return TokenDto
     */
    @PostMapping("/validate")
    public TokenDto validate(
            @RequestBody @Valid TokenDto tokenDTO
    ) {
        authService.verifyToken(tokenDTO.token());
        return tokenDTO;
    }

    /**
     * Verify otp, update user, promote role and return new token
     * @param code
     * @param principal
     * @return TokenDto
     */
    @GetMapping("/otp")
    @PreAuthorize("hasAuthority('user:update')")
    public TokenDto otp(
            @RequestParam String code,
            Principal principal
    ) {
        otpService.verifyOtp(code, principal.getName());

        userService.updateConfirmedByName(principal.getName(), true);
        userService.promoteRoleByName(principal.getName());

        return authService.loginByUsername(principal.getName());
    }
}
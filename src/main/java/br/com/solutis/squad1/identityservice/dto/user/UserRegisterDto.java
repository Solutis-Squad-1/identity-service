package br.com.solutis.squad1.identityservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterDto(
        @NotBlank
        String username,

        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}

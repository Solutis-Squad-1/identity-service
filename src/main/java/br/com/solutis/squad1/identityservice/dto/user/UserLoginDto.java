package br.com.solutis.squad1.identityservice.dto.user;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for user login.
 */
public record UserLoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}

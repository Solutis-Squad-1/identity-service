package br.com.solutis.squad1.identityservice.dto.user;

/**
 * DTO for user response.
 */
public record UserResponseDto(
        Long id,
        String username,
        String email
) {
}

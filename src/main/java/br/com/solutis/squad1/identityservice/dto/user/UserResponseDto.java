package br.com.solutis.squad1.identityservice.dto.user;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {
}

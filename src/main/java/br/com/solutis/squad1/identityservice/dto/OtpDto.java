package br.com.solutis.squad1.identityservice.dto;

import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;

/**
 * DTO for OTP response.
 */
public record OtpDto(
        UserResponseDto user,
        String code
) {
}

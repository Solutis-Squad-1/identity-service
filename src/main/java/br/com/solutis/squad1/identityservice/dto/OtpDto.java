package br.com.solutis.squad1.identityservice.dto;

import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;

public record OtpDto(
        UserResponseDto user,
        String code
) {
}

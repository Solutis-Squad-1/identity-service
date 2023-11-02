package br.com.solutis.squad1.identityservice.dto.user;

import br.com.solutis.squad1.identityservice.dto.AddressResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

public record UserResponseDetailedDto(
        Long id,
        String username,
        String email,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        AddressResponseDto address
) {
}

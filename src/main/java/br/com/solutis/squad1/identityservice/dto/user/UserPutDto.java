package br.com.solutis.squad1.identityservice.dto.user;

import br.com.solutis.squad1.identityservice.dto.AddressPutDto;

/**
 * DTO for user update.
 */
public record UserPutDto(
        String email,

        AddressPutDto address
) {
}

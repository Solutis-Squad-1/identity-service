package br.com.solutis.squad1.identityservice.dto.user;

import br.com.solutis.squad1.identityservice.dto.AddressPutDto;

public record UserPutDto(
        String email,

        AddressPutDto address
) {
}

package br.com.solutis.squad1.identityservice.dto;

public record AddressResponseDto(
        Long id,
        String street,
        String city,
        Integer number,
        String complement,
        String neighborhood,
        String zipCode
) {
}

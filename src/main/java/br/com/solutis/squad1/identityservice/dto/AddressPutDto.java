package br.com.solutis.squad1.identityservice.dto;

/**
 * DTO for address update.
 */
public record AddressPutDto(
        String street,
        String city,
        Integer number,
        String complement,
        String neighborhood,
        String zipCode
) {
}

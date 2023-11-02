package br.com.solutis.squad1.identityservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(
        @NotBlank
        String owner,
        @NotBlank
        @Email
        String emailFrom,
        @NotBlank
        @jakarta.validation.constraints.Email
        String emailTo,
        @NotBlank
        String subject,
        @NotBlank
        String text
) {
}

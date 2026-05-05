package ru.mtuci.lab.controllers.dto.airline;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PassengerRequest(
        @NotBlank
        @Size(max = 80)
        String firstName,

        @NotBlank
        @Size(max = 80)
        String lastName,

        @NotBlank
        @Email
        @Size(max = 160)
        String email,

        @NotBlank
        @Size(max = 40)
        String documentNumber
) {
}

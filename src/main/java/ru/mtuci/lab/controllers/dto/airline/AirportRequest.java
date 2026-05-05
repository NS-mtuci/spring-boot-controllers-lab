package ru.mtuci.lab.controllers.dto.airline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AirportRequest(
        @NotBlank
        @Size(max = 10)
        String code,

        @NotBlank
        @Size(max = 120)
        String name,

        @NotBlank
        @Size(max = 80)
        String city,

        @NotBlank
        @Size(max = 80)
        String country
) {
}

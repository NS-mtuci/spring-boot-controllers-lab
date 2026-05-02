package ru.mtuci.lab.controllers.dto.airline;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AircraftRequest(
        @NotBlank
        @Size(max = 80)
        String model,

        @NotBlank
        @Size(max = 20)
        String tailNumber,

        @Min(1)
        int seats
) {
}

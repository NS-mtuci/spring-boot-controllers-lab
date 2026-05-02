package ru.mtuci.lab.controllers.dto.airline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FlightStatusRequest(
        @NotBlank
        @Size(max = 30)
        String status
) {
}

package ru.mtuci.lab.controllers.dto.airline;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FlightRequest(
        @NotBlank
        @Size(max = 20)
        String flightNumber,

        @NotBlank
        @Size(max = 80)
        String departureCity,

        @NotBlank
        @Size(max = 80)
        String arrivalCity,

        @NotNull
        @Future
        LocalDateTime departureTime,

        @Min(1)
        long aircraftId,

        @NotBlank
        @Size(max = 30)
        String status
) {
}

package ru.mtuci.lab.controllers.dto.airline;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookSeatRequest(
        @NotBlank
        @Size(max = 120)
        String passengerName,

        @Min(1)
        long flightId,

        @NotBlank
        @Size(max = 10)
        String seatNumber
) {
}

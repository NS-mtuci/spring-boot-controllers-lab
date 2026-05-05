package ru.mtuci.lab.controllers.dto.airline;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookSeatRequest(
        @Min(1)
        long passengerId,

        @Min(1)
        long flightId,

        @NotBlank
        @Size(max = 10)
        String seatNumber
) {
}

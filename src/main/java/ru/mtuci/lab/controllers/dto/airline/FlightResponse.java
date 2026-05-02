package ru.mtuci.lab.controllers.dto.airline;

import java.time.LocalDateTime;

public record FlightResponse(
        long id,
        String flightNumber,
        String departureCity,
        String arrivalCity,
        LocalDateTime departureTime,
        long aircraftId,
        String status
) {
}

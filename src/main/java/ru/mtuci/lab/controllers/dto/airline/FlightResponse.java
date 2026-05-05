package ru.mtuci.lab.controllers.dto.airline;

import java.time.LocalDateTime;

public record FlightResponse(
        long id,
        String flightNumber,
        long departureAirportId,
        String departureAirportCode,
        long arrivalAirportId,
        String arrivalAirportCode,
        LocalDateTime departureTime,
        long aircraftId,
        String status
) {
}

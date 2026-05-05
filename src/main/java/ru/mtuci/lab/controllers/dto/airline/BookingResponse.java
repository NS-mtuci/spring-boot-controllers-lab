package ru.mtuci.lab.controllers.dto.airline;

public record BookingResponse(
        long id,
        long passengerId,
        String passengerFirstName,
        String passengerLastName,
        long flightId,
        String seatNumber,
        String status
) {
}

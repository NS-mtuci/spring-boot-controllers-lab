package ru.mtuci.lab.controllers.dto.airline;

public record AircraftResponse(
        long id,
        String model,
        String tailNumber,
        int seats
) {
}

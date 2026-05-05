package ru.mtuci.lab.controllers.dto.airline;

public record AirportResponse(
        long id,
        String code,
        String name,
        String city,
        String country
) {
}

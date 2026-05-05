package ru.mtuci.lab.controllers.dto.airline;

public record PassengerResponse(
        long id,
        String firstName,
        String lastName,
        String email,
        String documentNumber
) {
}

package ru.mtuci.lab.controllers.dto;

public record StudentResponse(
        long id,
        String fullName,
        String groupName
) {
}

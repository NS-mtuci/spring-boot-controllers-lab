package ru.mtuci.lab.controllers.dto.airline;

import jakarta.validation.constraints.Min;

public record AssignAircraftRequest(
        @Min(1)
        long aircraftId
) {
}

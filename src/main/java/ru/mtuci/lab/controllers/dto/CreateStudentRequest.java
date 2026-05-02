package ru.mtuci.lab.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateStudentRequest(
        @NotBlank
        @Size(max = 120)
        String fullName,

        @NotBlank
        @Size(max = 30)
        String groupName
) {
}

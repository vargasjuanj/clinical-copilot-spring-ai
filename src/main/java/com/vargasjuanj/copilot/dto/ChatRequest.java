package com.vargasjuanj.copilot.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank(message = "El prompt no puede estar vacío")
        String prompt,
        String model
) {
}

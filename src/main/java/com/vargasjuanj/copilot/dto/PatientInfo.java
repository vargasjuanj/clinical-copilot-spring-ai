package com.vargasjuanj.copilot.dto;

public record PatientInfo(
        String firstName,
        String lastName,
        String dateOfBirth,
        String allergies,
        String conditions
) {
}

package com.vargasjuanj.copilot.dto;

public record AppointmentInfo(
        String doctorName,
        String specialty,
        String date,
        String time
) {
}

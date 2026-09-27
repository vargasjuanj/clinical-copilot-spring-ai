package com.vargasjuanj.copilot.dto.analysis;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ConditionSummary(
        @JsonPropertyDescription("Nombre de la condición médica")
        String conditionName,

        @JsonPropertyDescription("Descripción accesible para pacientes, sin jerga médica innecesaria")
        String description,

        @JsonPropertyDescription("Síntomas más frecuentes asociados a esta condición")
        String commonSymptoms,

        @JsonPropertyDescription("Nivel de gravedad general de la condición")
        Severity severity,

        @JsonPropertyDescription("Indicaciones claras de cuándo buscar atención médica profesional")
        String whenToSeeDoctor
) {
}

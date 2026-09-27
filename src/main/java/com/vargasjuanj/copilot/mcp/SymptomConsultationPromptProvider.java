package com.vargasjuanj.copilot.mcp;

import io.modelcontextprotocol.spec.McpSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.mcp.annotation.McpArg;
import org.springframework.ai.mcp.annotation.McpPrompt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SymptomConsultationPromptProvider {

    @McpPrompt(
            name = "symptom-consultation",
            description = "Genera un mensaje estructurado para consultar sobre síntomas médicos"
    )
    public McpSchema.GetPromptResult symptomConsultation(
            @McpArg(
                    name = "symptom",
                    description = "Síntoma principal del paciente",
                    required = true
            ) String symptom
    ){
        String message = """
                Necesito orientación educativa sobre el siguiente síntoma: %s.
                
                Por favor incluí:
                - Posibles causas comunes
                - Cuándo consultar a un profesional
                - Especialidad médica recomendada
                
                Recordá que esta información es solo educativa y no reemplaza
                una consulta médica presencial.
                """.formatted(symptom);

        return new McpSchema.GetPromptResult(
                "Consulta sobre síntomas",
                List.of(new McpSchema.PromptMessage(McpSchema.Role.USER, new McpSchema.TextContent(message)))
        );

    }
}










package com.vargasjuanj.copilot.service;

import com.vargasjuanj.copilot.config.ClientResolver;
import com.vargasjuanj.copilot.dto.analysis.ConditionSummary;
import com.vargasjuanj.copilot.dto.analysis.QueryClassification;
import com.vargasjuanj.copilot.dto.analysis.SymptomAnalysis;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService{

    private final ClientResolver clientResolver;

    @Value("classpath:prompts/structured-analysis.st")
    private Resource structuredAnalysisResource;

    private PromptTemplate structuredAnalysisTemplate;

    @PostConstruct
    void init(){
        structuredAnalysisTemplate = new PromptTemplate(structuredAnalysisResource);
    }

    @Override
    public ConditionSummary summarizeCondition(String condition, String model, Long userId) {

        log.info("Análisis estructurado de condición: {}, modelo: {}", condition, model);

        return clientResolver.resolve(model)
                .prompt()
                .user("Proporcioná un resumen médico educativo sobre: " + condition)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .entity(ConditionSummary.class);


    }

    @Override
    public List<ConditionSummary> listRelatedConditions(String symptoms, String model, Long userId) {
        log.info("Listado de condiciones realacionadas - modelo: {} ", model);
        return clientResolver.resolve(model)
                .prompt()
                .user("Listá las 3 condiciones médicas más probables " +
                        "para estos síntomas: " + symptoms
                        )
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public SymptomAnalysis analyzeSymptoms(String symptoms, String model, Long userId) {
        log.info("Análisis estructurado de síntomas — modelo: {}", model);

        String message = structuredAnalysisTemplate.render(
                Map.of("sintomas", symptoms)
        );

        return clientResolver.resolve(model)
                .prompt()
                .user(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .entity(SymptomAnalysis.class);
    }

    @Override
    public QueryClassification classifyQuery(String query, String model, Long userId) {
        log.info("Clasificación de consulta — modelo: {}", model);

        return ChatClient.create(clientResolver.resolveModel(model))
                .prompt()
                .user("Clasificá la siguiente consulta de un paciente. " +
                        "Determiná qué tipo de consulta es y explicá brevemente por qué.\n\n" +
                        "Consulta del paciente: \"" + query + "\"")
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .entity(QueryClassification.class);
    }
}

















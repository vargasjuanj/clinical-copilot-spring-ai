package com.vargasjuanj.copilot.service;

import com.vargasjuanj.copilot.config.ClientResolver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssistantServiceImpl implements AssistantService{

    private final ClientResolver clientResolver;

    @Value("classpath:prompts/explain-condition.st")
    private Resource explainConditionPrompt;

    @Value("classpath:prompts/symptom-analysis.st")
    private Resource symptomAnalysisPrompt;

    @Value("classpath:prompts/diagnosis-cot.st")
    private Resource diagnosisCotResource;

    @Value("classpath:prompts/consultation.st")
    private Resource consultationResource;

    private PromptTemplate explainConditionTemplate;

    private PromptTemplate symptomAnalysisTemplate;

    private PromptTemplate diagnosisCotTemplate;

    private PromptTemplate consultationTemplate;

    private final ChatMemory chatMemory;
    private MessageChatMemoryAdvisor memoryAdvisor;

    @PostConstruct
    void init(){
        memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        consultationTemplate = new PromptTemplate(consultationResource);
        diagnosisCotTemplate = new PromptTemplate(diagnosisCotResource);
        symptomAnalysisTemplate = new PromptTemplate(symptomAnalysisPrompt);
        explainConditionTemplate = new PromptTemplate(explainConditionPrompt);
    }

    @Override
    public String chat(String prompt, String model, Long userId, String role) {
        log.info("Chat request - modelo: {} ", model);

        return clientResolver.resolve(model)
                .prompt(prompt)
                .toolContext(Map.of("userId", userId, "role", role))
                .advisors(a -> a.advisors(memoryAdvisor)
                        .param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .content();
    }

    @Override
    public Flux<String> chatStream(String prompt, String model, Long userId, String role) {
        log.info("Stream request - modelo: {}, userId: {}", model, userId);

        return clientResolver.resolve(model)
                .prompt(prompt)
                .toolContext(Map.of("userId", userId, "role", role))
                .advisors(a -> a.advisors(memoryAdvisor)
                        .param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .stream()
                .content();
    }

    @Override
    public String explainCondition(String condition, String model, Long userId) {
        log.info("Explain request — condición: {}, modelo: {}", condition, model);

        String message = explainConditionTemplate.render(Map.of("condicion", condition));

        return clientResolver.resolve(model)
                .prompt(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .content();
    }

    @Override
    public String analyzeSymptoms(String symptoms, String model, Long userId) {
        log.info("Análisis de síntomas: {}, modelo: {}", symptoms, model);

        String message = symptomAnalysisTemplate.render(Map.of("sintomas", symptoms));

        return clientResolver.resolve(model)
                .prompt(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .content();
    }

    @Override
    public String diagnoseWithReasoning(String symptoms, String model, Long userId) {
        log.info("Diagnóstico CoT — modelo: {}", model);

        String message = diagnosisCotTemplate.render(Map.of("sintomas", symptoms));

        return clientResolver.resolve(model)
                .prompt(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .content();
    }

    @Override
    public String consult(String query, String model, Long userId) {
        log.info("Consulta médica — modelo: {}", model);
        String message = consultationTemplate.render(Map.of("consulta", query));
        return clientResolver.resolve(model)
                .prompt(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, String.valueOf(userId)))
                .call()
                .content();
    }

}












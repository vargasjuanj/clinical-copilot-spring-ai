package com.vargasjuanj.copilot.service;

import com.vargasjuanj.copilot.dto.analysis.ConditionSummary;
import com.vargasjuanj.copilot.dto.analysis.QueryClassification;
import com.vargasjuanj.copilot.dto.analysis.SymptomAnalysis;

import java.util.List;

public interface AnalysisService {
    ConditionSummary summarizeCondition(String condition, String model, Long userId);
    List<ConditionSummary> listRelatedConditions(String symptoms, String model, Long userId);
    SymptomAnalysis analyzeSymptoms(String symptoms, String model, Long userId);
    QueryClassification classifyQuery(String query, String model, Long userId);
}

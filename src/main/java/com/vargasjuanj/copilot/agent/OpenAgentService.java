package com.vargasjuanj.copilot.agent;

public interface OpenAgentService {
    String execute(String query, String model, Long userId);
}

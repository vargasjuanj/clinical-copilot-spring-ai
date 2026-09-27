package com.vargasjuanj.copilot.agent;

public interface RoutingWorkflowService {
    String routeQuery(String query, String model, Long userId);
}

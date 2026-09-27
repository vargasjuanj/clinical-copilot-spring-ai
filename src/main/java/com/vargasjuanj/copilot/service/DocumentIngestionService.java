package com.vargasjuanj.copilot.service;

import com.vargasjuanj.copilot.dto.IngestionResult;
import org.springframework.core.io.Resource;

public interface DocumentIngestionService {

    IngestionResult ingestNewDocument(Resource resource);
}

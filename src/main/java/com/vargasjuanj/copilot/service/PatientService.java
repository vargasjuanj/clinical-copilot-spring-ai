package com.vargasjuanj.copilot.service;

import com.vargasjuanj.copilot.dto.PatientInfo;

public interface PatientService {
    PatientInfo getPatientInfo(Long patientId);
}

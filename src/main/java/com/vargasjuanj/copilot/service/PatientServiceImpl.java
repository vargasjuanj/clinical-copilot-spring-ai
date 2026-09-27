package com.vargasjuanj.copilot.service;

import com.vargasjuanj.copilot.dto.PatientInfo;
import com.vargasjuanj.copilot.model.Patient;
import com.vargasjuanj.copilot.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    @Override
    public PatientInfo getPatientInfo(Long patientId) {

        log.info("Consultando historial: patientId={}", patientId);

        return patientRepository.findById(patientId)
                .map(this::toPatientInfo)
                .orElse(null);

    }

    private PatientInfo toPatientInfo(Patient patient) {
        return new PatientInfo(
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth().toString(),
                patient.getAllergies(),
                patient.getConditions());
    }
}

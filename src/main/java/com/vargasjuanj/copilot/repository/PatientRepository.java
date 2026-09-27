package com.vargasjuanj.copilot.repository;

import com.vargasjuanj.copilot.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}

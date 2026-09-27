package com.vargasjuanj.copilot.service;

import com.vargasjuanj.copilot.dto.DoctorInfo;

import java.util.List;

public interface DoctorService {
    List<DoctorInfo> searchDoctors(String query);
}

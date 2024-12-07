package com.jpacourse.service;

import com.jpacourse.dto.patient.PatientTO;

public interface PatientService {
    public PatientTO findById(final Long id);
}

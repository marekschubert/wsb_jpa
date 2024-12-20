package com.jpacourse.service;

import com.jpacourse.dto.patient.PatientTO;

public interface PatientService {
     PatientTO findById(final Long id);

     void deleteById(final Long id);
}

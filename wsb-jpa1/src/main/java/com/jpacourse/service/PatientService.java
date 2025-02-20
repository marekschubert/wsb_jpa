package com.jpacourse.service;

import com.jpacourse.dto.patient.AddPatientVisitTO;
import com.jpacourse.dto.patient.PatientTO;
import com.jpacourse.dto.patient.UpdatePatientTO;

public interface PatientService {
     PatientTO findById(final Long id);

     void deleteById(final Long id);

     void addVisit(AddPatientVisitTO addPatientVisitTO);

     void update(UpdatePatientTO updatePatientTO);
}

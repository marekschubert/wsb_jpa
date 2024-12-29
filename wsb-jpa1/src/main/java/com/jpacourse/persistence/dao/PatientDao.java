package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {
    void customSavePatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);
    List<PatientEntity> findByLastName(String lastName);
    List<PatientEntity> findWithVisitsCountGreaterThan(int visitsCount);
    List<PatientEntity> findWithHeightLessThan(int height);
}



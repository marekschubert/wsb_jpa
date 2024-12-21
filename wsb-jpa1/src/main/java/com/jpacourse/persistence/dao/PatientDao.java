package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;

import java.time.LocalDateTime;

public interface PatientDao extends Dao<PatientEntity, Long>
{
    void customSavePatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);
}


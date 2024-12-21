package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.rest.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{
    @Autowired
    DoctorDao doctorDao;

    @Override
    public void customSavePatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) {
        PatientEntity patientEntity = findOne(patientId);

        if (patientEntity == null) {
            throw new EntityNotFoundException(patientId);
        }

        DoctorEntity doctorEntity = doctorDao.findOne(doctorId);

        if(doctorEntity == null)  {
            throw new EntityNotFoundException(doctorId);
        }

        VisitEntity visit = new VisitEntity();
        visit.setDoctor(doctorEntity);
        visit.setPatient(patientEntity);
        visit.setTime(visitDate);
        visit.setDescription(visitDescription);

        if(patientEntity.getVisits() == null) {
            patientEntity.setVisits(new ArrayList<>());
        }

        patientEntity.getVisits().add(visit);

        update(patientEntity);
    }
}

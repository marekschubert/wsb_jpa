package com.jpacourse.service.impl;

import com.jpacourse.dto.patient.AddPatientVisitTO;
import com.jpacourse.dto.patient.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Transactional
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao)
    {
        this.patientDao = patientDao;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public void deleteById(Long id) {
        patientDao.delete(id);
    }

    @Override
    public void addVisit(AddPatientVisitTO addPatientVisitTO) {

        LocalDateTime formattedDate = LocalDateTime.parse(addPatientVisitTO.getVisitDate());
        patientDao.customSavePatient(addPatientVisitTO.getPatientId(), addPatientVisitTO.getDoctorId(), formattedDate, addPatientVisitTO.getVisitDescription());
    }
}

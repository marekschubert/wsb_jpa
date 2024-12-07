package com.jpacourse.mapper;

import com.jpacourse.dto.visit.PatientVisitTO;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.stream.Collectors;

public class VisitMapper {
    private VisitMapper()
    {

    }

    public static PatientVisitTO mapToPatientVisitTO(final VisitEntity visitEntity){
        if (visitEntity == null)
        {
            return null;
        }
        DoctorEntity doctorEntity = visitEntity.getDoctor();
        final PatientVisitTO patientVisitTO = new PatientVisitTO();

        patientVisitTO.setId(visitEntity.getId());
        patientVisitTO.setDoctorFirstName(doctorEntity.getFirstName());
        patientVisitTO.setDoctorLastName(doctorEntity.getLastName());
        patientVisitTO.setTime(visitEntity.getTime());

        patientVisitTO.setMedicalTreatments(visitEntity.getMedicalTreatment()
                .stream()
                .map(MedicalTreatmentMapper::mapToTO)
                .collect(Collectors.toList()));

        return patientVisitTO;
    }
}
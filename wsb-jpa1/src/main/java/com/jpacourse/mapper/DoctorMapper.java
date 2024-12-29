package com.jpacourse.mapper;

import com.jpacourse.dto.doctor.VisitDoctorTO;
import com.jpacourse.persistence.entity.DoctorEntity;

public class DoctorMapper {
    public static VisitDoctorTO mapToVisitDoctorTO(final DoctorEntity doctorEntity){
        if (doctorEntity == null)
        {
            return null;
        }

        VisitDoctorTO visitDoctorTO = new VisitDoctorTO();

        visitDoctorTO.setId(doctorEntity.getId());
        visitDoctorTO.setFirstName(doctorEntity.getFirstName());
        visitDoctorTO.setLastName(doctorEntity.getLastName());
        visitDoctorTO.setTelephoneNumber(doctorEntity.getTelephoneNumber());
        visitDoctorTO.setEmail(doctorEntity.getEmail());
        visitDoctorTO.setDoctorNumber(doctorEntity.getDoctorNumber());
        visitDoctorTO.setSpecialization(doctorEntity.getSpecialization());

        return visitDoctorTO;
    }
}
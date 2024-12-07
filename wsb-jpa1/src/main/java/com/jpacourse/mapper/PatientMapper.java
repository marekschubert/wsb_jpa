package com.jpacourse.mapper;

import com.jpacourse.dto.address.AddressTO;
import com.jpacourse.dto.patient.PatientTO;
import com.jpacourse.dto.visit.PatientVisitTO;
import com.jpacourse.persistence.entity.PatientEntity;

import java.util.List;
import java.util.stream.Collectors;


public class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity){
        if (patientEntity == null)
        {
            return null;
        }
        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setHeight(patientEntity.getHeight());

        AddressTO addressTO = AddressMapper.mapToTO(patientEntity.getAddress());
        patientTO.setAddress(addressTO);

        List<PatientVisitTO> visits = patientEntity
                .getVisits()
                .stream()
                .map(VisitMapper::mapToPatientVisitTO)
                .collect(Collectors.toList());

        patientTO.setVisits(visits);

        return patientTO;
    }

}

package com.jpacourse.service;

import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.impl.DoctorDaoImpl;
import com.jpacourse.persistence.dao.impl.PatientDaoImpl;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    PatientDaoImpl patientDao;

    @Autowired
    DoctorDaoImpl doctorDao;

    @Autowired
    AddressDao addressDao;

    @Test
    @Transactional
    public void testCustomSavePatient() {
        // Given
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Jan");
        patientEntity.setLastName("Kowalski");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("jan.kowalski@example.com");
        patientEntity.setPatientNumber("P12345");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 5, 15));
        patientEntity.setHeight(180);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressLine1("Test address 1");
        addressEntity.setCity("Wrocław");
        addressEntity.setPostalCode("00-001");

        patientEntity.setAddress(addressEntity);

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setFirstName("Adam");
        doctorEntity.setLastName("Nowak");
        doctorEntity.setTelephoneNumber("123456789");
        doctorEntity.setEmail("adam.nowak@clinic.com");
        doctorEntity.setDoctorNumber("D12345");
        doctorEntity.setSpecialization(Specialization.DERMATOLOGIST);

        //same test address
        doctorEntity.setAddress(addressEntity);

        LocalDateTime visitDate = LocalDateTime.of(2025, 12, 12, 10, 10);
        String visitDescription = "Visit description";

        // When
        addressDao.save(addressEntity);
        assertThat(addressEntity.getId()).isNotNull();
        patientDao.save(patientEntity);
        assertThat(patientEntity.getId()).isNotNull();
        doctorDao.save(doctorEntity);
        assertThat(doctorEntity.getId()).isNotNull();


        patientDao.customSavePatient(patientEntity.getId(), doctorEntity.getId(), visitDate, visitDescription);

        // Then
        PatientEntity patient = patientDao.findOne(patientEntity.getId());
        assertThat(patient).isNotNull();
        assertEquals(1, patient.getVisits().size());

        VisitEntity addedVisit = patient.getVisits().iterator().next();

        assertEquals(doctorEntity.getId(), addedVisit.getDoctor().getId());
        assertEquals(visitDate, addedVisit.getTime());
        assertEquals(visitDescription, addedVisit.getDescription());
    }

}

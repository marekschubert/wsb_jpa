package com.jpacourse.service;

import com.jpacourse.dto.address.AddressTO;
import com.jpacourse.dto.medicalTreatment.MedicalTreatmentTO;
import com.jpacourse.dto.patient.PatientTO;
import com.jpacourse.dto.patient.UpdatePatientTO;
import com.jpacourse.dto.visit.PatientVisitTO;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.enums.TreatmentType;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private VisitDao visitDao;

    @Autowired
    private DoctorDao doctorDao;

    @Transactional
    @Test
    public void testShouldFindPatientById() {
        // given
        Long patientId = 1L;
        // when
        PatientTO patientTO = patientService.findById(patientId);
        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getId()).isEqualTo(patientId);

        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getId()).isEqualTo(patientId);
        assertThat(patientTO.getFirstName()).isEqualTo("Piotr");
        assertThat(patientTO.getLastName()).isEqualTo("Zieliński");
        assertThat(patientTO.getTelephoneNumber()).isEqualTo("111222333");
        assertThat(patientTO.getEmail()).isEqualTo("piotr.zielinski@gmail.com");
        assertThat(patientTO.getPatientNumber()).isEqualTo("PAT001");
        assertThat(patientTO.getHeight()).isEqualTo(180);
        assertThat(patientTO.getDateOfBirth().toString()).isEqualTo("1990-05-15");

        AddressTO address = patientTO.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getId()).isEqualTo(3L);
        assertThat(address.getCity()).isEqualTo("Wrocław");
        assertThat(address.getAddressLine1()).isEqualTo("Plac Grunwaldzki 20");
        assertThat(address.getAddressLine2()).isNull();
        assertThat(address.getPostalCode()).isEqualTo("50-001");

        // Verify visits
        Collection<PatientVisitTO> visits = patientTO.getVisits();
        assertThat(visits).isNotNull();
        assertThat(visits).hasSize(3);

        // Verify the structure of the first visit
        PatientVisitTO firstVisit = visits.iterator().next();
        assertThat(firstVisit).isNotNull();
        assertThat(firstVisit.getId()).isEqualTo(1L);
        assertThat(firstVisit.getTime()).isEqualTo(LocalDateTime.parse("2024-11-26T10:00:00"));
        assertThat(firstVisit.getDoctorFirstName()).isEqualTo("Jan");
        assertThat(firstVisit.getDoctorLastName()).isEqualTo("Kowalski");

        // Verify medical treatments of the first visit
        Collection<MedicalTreatmentTO> treatments = firstVisit.getMedicalTreatments();
        assertThat(treatments).isNotNull();
        assertThat(treatments).hasSize(2);

        MedicalTreatmentTO firstTreatment = treatments.iterator().next();
        assertThat(firstTreatment).isNotNull();
        assertThat(firstTreatment.getId()).isEqualTo(1L);
        assertThat(firstTreatment.getDescription()).isEqualTo("ECHO serca");
        assertThat(firstTreatment.getType()).isEqualTo(TreatmentType.EKG);
    }

    @Transactional
    @Test
    public void testShouldUpdatePatient() {
        // given
        Long patientId = 1L;
        PatientEntity existingPatient = patientDao.findOne(patientId);
        assertThat(existingPatient).isNotNull();

        UpdatePatientTO updatePatientTO = new UpdatePatientTO();
        updatePatientTO.setId(patientId);
        updatePatientTO.setFirstName("UpdatedFirstName");
        //LastName not update - should remain the same

        updatePatientTO.setTelephoneNumber("123456789");
        updatePatientTO.setEmail("updatedemail@example.com");
        updatePatientTO.setPatientNumber("PN12345");
        updatePatientTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        updatePatientTO.setHeight(180);

        AddressTO addressTO = new AddressTO();
        addressTO.setAddressLine1("Updated Address Line 1");
        addressTO.setCity("Updated City");
        updatePatientTO.setAddress(addressTO);

        // when
        patientService.update(updatePatientTO);

        // then
        PatientEntity updatedPatient = patientDao.findOne(patientId);
        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getFirstName()).isEqualTo("UpdatedFirstName");
        assertThat(updatedPatient.getTelephoneNumber()).isEqualTo("123456789");
        assertThat(updatedPatient.getEmail()).isEqualTo("updatedemail@example.com");
        assertThat(updatedPatient.getPatientNumber()).isEqualTo("PN12345");
        assertThat(updatedPatient.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(updatedPatient.getHeight()).isEqualTo(180);
        assertThat(updatedPatient.getAddress()).isNotNull();
        assertThat(updatedPatient.getAddress().getAddressLine1()).isEqualTo("Updated Address Line 1");
        assertThat(updatedPatient.getAddress().getCity()).isEqualTo("Updated City");

        assertThat(updatedPatient.getLastName()).isEqualTo(existingPatient.getLastName());
    }


    @Transactional
    @Test
    public void testShouldRemovePatient() {
        // given
        Long patientId = 1L;
        assertThat(patientService.findById(patientId)).isNotNull();
        assertThat(visitDao.findAllByPatientId(patientId)).isNotEmpty();

        long initialDoctorCount = doctorDao.count();

        // when
        patientService.deleteById(patientId);

        // then
        assertThat(patientService.findById(patientId)).isNull();
        assertThat(visitDao.findAllByPatientId(patientId)).isEmpty();

        long finalDoctorCount = doctorDao.count();
        assertThat(finalDoctorCount).isEqualTo(initialDoctorCount);
    }
}

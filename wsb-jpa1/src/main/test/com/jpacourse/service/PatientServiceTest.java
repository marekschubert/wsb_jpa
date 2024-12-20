package com.jpacourse.service;

import com.jpacourse.dto.address.AddressTO;
import com.jpacourse.dto.medicalTreatment.MedicalTreatmentTO;
import com.jpacourse.dto.patient.PatientTO;
import com.jpacourse.dto.visit.PatientVisitTO;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.enums.TreatmentType;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientServiceImpl patientService;

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

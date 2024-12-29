package com.jpacourse.service;

import com.jpacourse.dto.doctor.VisitDoctorTO;
import com.jpacourse.dto.medicalTreatment.MedicalTreatmentTO;
import com.jpacourse.dto.patient.VisitPatientTO;
import com.jpacourse.dto.visit.VisitTO;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.enums.TreatmentType;
import com.jpacourse.service.impl.VisitServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Transactional
    @Test
    public void testShouldFindAllVisitsByPatientId() {
        // given
        Long patientId = 1L;

        // when
        List<VisitTO> visits = visitService.findAllByPatientId(patientId);

        // then
        assertNotNull(visits);
        assertThat(visits).hasSize(3);

        // Verify the structure of the first visit
        VisitTO firstVisit = visits.get(0);
        assertThat(firstVisit).isNotNull();
        assertThat(firstVisit.getId()).isEqualTo(1L);
        assertThat(firstVisit.getDescription()).isEqualTo("Kontrola pooperacyjna");
        assertThat(firstVisit.getTime()).isEqualTo(LocalDateTime.parse("2024-11-26T10:00:00"));

        // Verify doctor details
        VisitDoctorTO doctor = firstVisit.getDoctor();
        assertThat(doctor).isNotNull();
        assertThat(doctor.getFirstName()).isEqualTo("Jan");
        assertThat(doctor.getLastName()).isEqualTo("Kowalski");

        // Verify patient details
        VisitPatientTO patient = firstVisit.getPatient();
        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isEqualTo(1L);
        assertThat(patient.getFirstName()).isEqualTo("Piotr");
        assertThat(patient.getLastName()).isEqualTo("Zieliński");

        // Verify medical treatments
        Collection<MedicalTreatmentTO> treatments = firstVisit.getMedicalTreatment();
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
    public void testShouldReturnEmptyListWhenNoVisitsForPatient() {
        // given
        Long patientId = 4L;

        // when
        List<VisitTO> visits = visitService.findAllByPatientId(patientId);

        // then
        assertNotNull(visits);
        assertThat(visits).isEmpty();
    }
}

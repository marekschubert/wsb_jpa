package com.jpacourse.persistance.dao;

import com.jpacourse.dto.address.AddressTO;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    PatientDao patientDao;

    @Autowired
    DoctorDao doctorDao;

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

    @Test
    @Transactional
    public void testFindByLastName_Exist()
    {
        // given
        String lastName = "Kowalski";

        // when
        List<PatientEntity> result = patientDao.findByLastName(lastName);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getLastName(), lastName);
        assertEquals(result.get(1).getLastName(), lastName);
    }

    @Test
    @Transactional
    public void testFindByLastName_NotExist()
    {
        // given
        String lastName = "NotExistingSurname";

        // when
        List<PatientEntity> result = patientDao.findByLastName(lastName);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    public void testFindPatientsWithVisitsCountGreaterThan_OneAbove() {
        // given
        int visitsCount = 2;

        // when
        List<PatientEntity> result = patientDao.findWithVisitsCountGreaterThan(visitsCount);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        PatientEntity patient = result.get(0);
        assertEquals("Piotr", patient.getFirstName());

        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isEqualTo(1L);
        assertThat(patient.getFirstName()).isEqualTo("Piotr");
        assertThat(patient.getLastName()).isEqualTo("Zieliński");
        assertThat(patient.getTelephoneNumber()).isEqualTo("111222333");
        assertThat(patient.getEmail()).isEqualTo("piotr.zielinski@gmail.com");
        assertThat(patient.getPatientNumber()).isEqualTo("PAT001");
        assertThat(patient.getHeight()).isEqualTo(180);
        assertThat(patient.getDateOfBirth().toString()).isEqualTo("1990-05-15");

        AddressEntity address = patient.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getId()).isEqualTo(3L);
        assertThat(address.getCity()).isEqualTo("Wrocław");
        assertThat(address.getAddressLine1()).isEqualTo("Plac Grunwaldzki 20");
        assertThat(address.getAddressLine2()).isNull();
        assertThat(address.getPostalCode()).isEqualTo("50-001");
    }

    @Test
    @Transactional
    public void testFindPatientsWithVisitsCountGreaterThan_ManyAbove() {
        // given
        int visitThreshold = 1;

        // when
        List<PatientEntity> result = patientDao.findWithVisitsCountGreaterThan(visitThreshold);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        PatientEntity patient1 = result
                .stream()
                .filter(x -> x.getId() == 1L)
                .findFirst()
                .orElse(null);

        assertNotNull(patient1);
        assertEquals("Piotr", patient1.getFirstName());
        assertEquals("Zieliński", patient1.getLastName());
        assertEquals("PAT001", patient1.getPatientNumber());
        assertEquals("Wrocław", patient1.getAddress().getCity());
        assertThat(patient1.getHeight()).isEqualTo(180);

        PatientEntity patient2 = result
                .stream()
                .filter(x -> x.getId() == 2L)
                .findFirst()
                .orElse(null);

        assertNotNull(patient2);
        assertEquals("Katarzyna", patient2.getFirstName());
        assertEquals("Wiśniewska", patient2.getLastName());
        assertEquals("PAT002", patient2.getPatientNumber());
        assertEquals("Kraków", patient2.getAddress().getCity());
        assertThat(patient2.getHeight()).isEqualTo(170);
    }

    @Test
    @Transactional
    public void testFindPatientsWithVisitsCountGreaterThan_NotExist() {
        // given
        int visitsCount = 3;

        // when
        List<PatientEntity> result = patientDao.findWithVisitsCountGreaterThan(visitsCount);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    public void testFindPatientsWithHeightLessThan_Exist() {
        // given
        int height = 170;

        // when
        List<PatientEntity> result = patientDao.findWithHeightLessThan(height);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        PatientEntity patient = result
                .stream()
                .filter(x -> x.getId() == 4L)
                .findFirst()
                .orElse(null);

        assertNotNull(patient);
        assertEquals("Adam", patient.getFirstName());
        assertEquals("Kowalski", patient.getLastName());
        assertEquals("PAT004", patient.getPatientNumber());
        assertEquals("Wrocław", patient.getAddress().getCity());
        assertThat(patient.getHeight()).isEqualTo(165);
    }

    @Test
    @Transactional
    public void testFindPatientsWithHeightLessThan_NotExist() {
        // given
        int height = 165;

        // when
        List<PatientEntity> result = patientDao.findWithHeightLessThan(height);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    public void testOptimisticLocking_ConcurrentModification() throws InterruptedException {
        // given
        Long patientId = 4L;

        Thread t1 = new Thread(() -> {
            PatientEntity patient1 = patientDao.findOne(patientId);
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            patient1.setTelephoneNumber("111111111");

            // then
            assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
                patientDao.merge(patient1);
            });
        });

        Thread t2 = new Thread(() -> {
            PatientEntity patient2 = patientDao.findOne(patientId);
            patient2.setTelephoneNumber("999999999");
            patientDao.merge(patient2);
        });

        // when
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

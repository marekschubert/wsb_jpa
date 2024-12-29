package com.jpacourse.dto.visit;

import com.jpacourse.dto.doctor.VisitDoctorTO;
import com.jpacourse.dto.medicalTreatment.MedicalTreatmentTO;
import com.jpacourse.dto.patient.VisitPatientTO;

import java.time.LocalDateTime;
import java.util.Collection;

public class VisitTO {
    private Long id;
    private String description;
    private LocalDateTime time;
    private VisitDoctorTO doctor;
    private VisitPatientTO patient;
    private Collection<MedicalTreatmentTO> medicalTreatment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public VisitDoctorTO getDoctor() {
        return doctor;
    }

    public void setDoctor(VisitDoctorTO doctor) {
        this.doctor = doctor;
    }

    public VisitPatientTO getPatient() {
        return patient;
    }

    public void setPatient(VisitPatientTO patient) {
        this.patient = patient;
    }

    public Collection<MedicalTreatmentTO> getMedicalTreatment() {
        return medicalTreatment;
    }

    public void setMedicalTreatment(Collection<MedicalTreatmentTO> medicalTreatment) {
        this.medicalTreatment = medicalTreatment;
    }
}
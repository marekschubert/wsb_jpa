package com.jpacourse.dto.visit;

import com.jpacourse.dto.medicalTreatment.MedicalTreatmentTO;
import com.jpacourse.persistence.entity.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

public class PatientVisitTO implements Serializable {
    private Long id;

    private LocalDateTime time;

    private String doctorFirstName;

    private String doctorLastName;

    private Collection<MedicalTreatmentTO> medicalTreatments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public Collection<MedicalTreatmentTO> getMedicalTreatments() {
        return medicalTreatments;
    }

    public void setMedicalTreatments(Collection<MedicalTreatmentTO> medicalTreatments) {
        this.medicalTreatments = medicalTreatments;
    }

}

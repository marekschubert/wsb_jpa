package com.jpacourse.persistence.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "VISIT")
@NamedQueries({
		@NamedQuery(
				name = "VisitEntity.findAllByPatientId",
				query = "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId"
		)
})
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	@ManyToOne(optional = false)
	@JoinColumn(name = "doctor_id")
	// Relacja dwustronna z DoctorEntity
	private DoctorEntity doctor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "patient_id")
	// Relacja dwustronna z PatientEntity
	private PatientEntity patient;

	@OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
	// Relacja dwustronna z MedicalTreatmentEntity
	private Collection<MedicalTreatmentEntity> medicalTreatment;

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

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public Collection<MedicalTreatmentEntity> getMedicalTreatment() {
		return medicalTreatment;
	}

	public void setMedicalTreatment(Collection<MedicalTreatmentEntity> medicalTreatment) {
		this.medicalTreatment = medicalTreatment;
	}
}

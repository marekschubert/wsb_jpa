package com.jpacourse.persistence.entity;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "PATIENT")
@NamedQueries({
		@NamedQuery(
				name = "PatientEntity.findByLastName",
				query = "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName"
		),
		@NamedQuery(
				name = "PatientEntity.findWithVisitsCountGreaterThan",
				query = "SELECT p FROM PatientEntity p WHERE size(p.visits) > :visitsCount"
		),
		@NamedQuery(
				name = "PatientEntity.findWithHeightLessThan",
				query = "SELECT p FROM PatientEntity p WHERE p.height < :height"
		)
})
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	@Column(columnDefinition = "bigint DEFAULT 0", nullable = false)
	private int version;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String telephoneNumber;

	@Column
	private String email;

	@Column(nullable = false)
	private String patientNumber;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	private Integer height;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "address_id", unique = true, nullable = false)
	// Relacja jednostronna od strony rodzica z AddressEntity
	private AddressEntity address;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	// Relacja dwustronna z VisitEntity
	private Collection<VisitEntity> visits;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getHeight() { return height;	}

	public void setHeight(Integer height) { this.height = height; }

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public Collection<VisitEntity> getVisits() {
		return visits;
	}

	public void setVisits(Collection<VisitEntity> visits) {
		this.visits = visits;
	}
}

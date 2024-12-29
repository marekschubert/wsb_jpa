package com.jpacourse.service;

import com.jpacourse.dto.address.AddressTO;
import com.jpacourse.dto.visit.VisitTO;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.List;

public interface VisitService {
    public List<VisitTO> findAllByPatientId(final Long patientId);
}
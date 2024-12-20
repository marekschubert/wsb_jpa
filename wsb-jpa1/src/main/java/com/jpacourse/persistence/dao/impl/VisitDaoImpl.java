package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VisitDaoImpl extends AbstractDao<VisitEntity, Long> implements VisitDao {

    @Override
    public List<VisitEntity> findAllByPatientId(Long patientId) {
        return findAll()
                .stream()
                .filter(visit -> visit.getPatient().getId().equals(patientId))
                .collect(Collectors.toList());
    }
}

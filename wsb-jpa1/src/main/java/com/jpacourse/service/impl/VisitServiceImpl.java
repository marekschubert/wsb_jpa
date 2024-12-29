package com.jpacourse.service.impl;

import com.jpacourse.dto.visit.VisitTO;
import com.jpacourse.mapper.AddressMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class VisitServiceImpl implements VisitService {
    private final VisitDao visitDao;

    @Autowired
    public VisitServiceImpl(VisitDao visitDao)
    {
        this.visitDao = visitDao;
    }

    @Override
    public List<VisitTO> findAllByPatientId(Long patientId) {
        final List<VisitEntity> entities = visitDao.findAllByPatientId(patientId);
        List<VisitTO> visitTOs = entities
                .stream()
                .map(VisitMapper::mapToTO)
                .collect(Collectors.toList());

        return visitTOs;
    }
}

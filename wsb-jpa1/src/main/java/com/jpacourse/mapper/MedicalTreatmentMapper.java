package com.jpacourse.mapper;

import com.jpacourse.dto.medicalTreatment.MedicalTreatmentTO;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;

public class MedicalTreatmentMapper {
    private MedicalTreatmentMapper()
    {

    }
    public static MedicalTreatmentTO mapToTO(final MedicalTreatmentEntity medicalTreatmentEntity) {
        if (medicalTreatmentEntity == null) {
            return null;
        }

        final MedicalTreatmentTO medicalTreatmentTO = new MedicalTreatmentTO();

        medicalTreatmentTO.setId(medicalTreatmentEntity.getId());
        medicalTreatmentTO.setDescription(medicalTreatmentEntity.getDescription());
        medicalTreatmentTO.setType(medicalTreatmentEntity.getType());

        return medicalTreatmentTO;
    }
}

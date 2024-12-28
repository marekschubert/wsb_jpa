package com.jpacourse.rest;

import com.jpacourse.dto.patient.AddPatientVisitTO;
import com.jpacourse.dto.patient.PatientTO;
import com.jpacourse.dto.patient.UpdatePatientTO;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient/{id}")
    PatientTO findById(@PathVariable final Long id) {
        final PatientTO patient = patientService.findById(id);
        if(patient != null)
        {
            return patient;
        }
        throw new EntityNotFoundException(id);
    }

    @PostMapping("/patient")
    void addPatientVisit(@RequestBody final AddPatientVisitTO addPatientVisitTO) {
        patientService.addVisit(addPatientVisitTO);
    }

    @DeleteMapping("/patient/{id}")
    void deletePatient(@PathVariable final Long id){
        patientService.deleteById(id);
    }

    @PutMapping("/patient")
    void updatePatient(@RequestBody final UpdatePatientTO updatePatientTO){
        patientService.update(updatePatientTO);
    }

}

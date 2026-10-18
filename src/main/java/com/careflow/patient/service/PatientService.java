package com.careflow.patient.service;

import com.careflow.patient.dto.CreatePatientRequest;
import com.careflow.patient.dto.PatientResponse;
import com.careflow.patient.dto.UpdatePatientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    PatientResponse createPatient(CreatePatientRequest request);
    PatientResponse getPatientById(Long id);
    Page<PatientResponse> getAllPatients(Pageable pageable);
    PatientResponse deactivatePatient(Long id);
    PatientResponse updatePatient(long id, UpdatePatientRequest request);
    Page<PatientResponse> searchPatients(String search,Long organizationId,Boolean active,Pageable pageable);
}

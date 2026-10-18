package com.careflow.practitioner.service;

import com.careflow.practitioner.dto.CreatePractitionerRequest;
import com.careflow.practitioner.dto.PractitionerResponse;
import com.careflow.practitioner.dto.UpdatePractitionerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PractitionerService {

    PractitionerResponse createPractitiner(CreatePractitionerRequest request);
    PractitionerResponse getPractitionerById(Long id);
    Page<PractitionerResponse> getAllPractitioners(Pageable pageable);
    PractitionerResponse updatePractitioner(Long id,UpdatePractitionerRequest request);
    PractitionerResponse deactivatePractitioner(Long id);
    Page<PractitionerResponse> searchPractitioners(
            String search,
            String specialization,
            String gender,
            Long organizationId,
            Boolean active,
            Pageable pageable);
}

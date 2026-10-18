package com.careflow.patient.service;

import com.careflow.common.exception.DuplicateResourceException;
import com.careflow.common.exception.ResourceNotFoundException;
import com.careflow.organization.entity.Organization;
import com.careflow.organization.repository.OrganizationRepository;
import com.careflow.patient.dto.CreatePatientRequest;
import com.careflow.patient.dto.PatientResponse;
import com.careflow.patient.dto.UpdatePatientRequest;
import com.careflow.patient.entity.Patient;
import com.careflow.patient.mapper.PatientMapper;
import com.careflow.patient.repository.PatientRepository;
import com.careflow.patient.specification.PatientSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientServiceImp implements   PatientService{

    public PatientServiceImp(PatientMapper patientMapper, PatientRepository patientRepository, OrganizationRepository organizationRepository) {
        this.patientMapper = patientMapper;
        this.patientRepository = patientRepository;
        this.organizationRepository = organizationRepository;
    }

    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;
    private final OrganizationRepository organizationRepository;


    @Override
    @Transactional
    public PatientResponse createPatient(CreatePatientRequest request) {

        if (patientRepository.existsByPatientCode(request.patientCode())) {
            throw new DuplicateResourceException("Patient code already Exists: "+request.patientCode());
        }

        Organization organization = organizationRepository
                .findById(request.organizationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with id: " + request.organizationId()
                ));
        Patient patient=patientMapper.toEntity(request,organization);
        Patient savedPatient=patientRepository.save(patient);

        return patientMapper.toResponse(savedPatient);



    }
    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id ){
        Patient patient=patientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Patient not found with id:"+id));
        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patientMapper::toResponse);
    }

    @Override
    @Transactional
    public  PatientResponse deactivatePatient(Long id){
        Patient patient=patientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Patient not found with id: "+id));
        patient.setActive(false);
        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional
    public PatientResponse updatePatient(long id, UpdatePatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + id
                ));
    Organization organization = organizationRepository.findById(request.organizationId()).orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with id: " + request.organizationId()));

        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setGender(request.gender());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setBloodGroup(request.bloodGroup());
        patient.setPhone(request.phone());
        patient.setEmail(request.email());

        patient.setAddressLine1(request.addressLine1());
        patient.setAddressLine2(request.addressLine2());
        patient.setCity(request.city());
        patient.setState(request.state());
        patient.setPostalCode(request.postalCode());
        patient.setCountry(request.country());

        patient.setEmergencyContactName(request.emergencyContactName());
        patient.setEmergencyContactPhone(request.emergencyContactPhone());
        patient.setOrganization(organization);

        return patientMapper.toResponse(patient);

    }
    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> searchPatients(
            String search,
            Long organizationId,
            Boolean active,
            Pageable pageable) {

        Specification<Patient> spec = Specification.unrestricted();

        if (search != null && !search.isBlank()) {
            spec = spec.and(
                    PatientSpecification.nameContains(search.trim())
            );
        }

        if (organizationId != null) {
            spec = spec.and(
                    PatientSpecification.hasOrganizationId(organizationId)
            );
        }

        if (active != null) {
            spec = spec.and(
                    PatientSpecification.hasActive(active)
            );
        }

        return patientRepository.findAll(spec, pageable)
                .map(patientMapper::toResponse);
    }

}

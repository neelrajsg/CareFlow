package com.careflow.practitioner.service;

import com.careflow.common.exception.DuplicateResourceException;
import com.careflow.common.exception.ResourceNotFoundException;
import com.careflow.organization.entity.Organization;
import com.careflow.organization.repository.OrganizationRepository;
import com.careflow.practitioner.dto.CreatePractitionerRequest;
import com.careflow.practitioner.dto.PractitionerResponse;
import com.careflow.practitioner.dto.UpdatePractitionerRequest;
import com.careflow.practitioner.entity.Practitioner;
import com.careflow.practitioner.mapper.PractitionerMapper;
import com.careflow.practitioner.repository.PractitionerRepository;
import com.careflow.practitioner.specification.PractitionerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PractitionerServiceImpl implements PractitionerService {

    public PractitionerServiceImpl(PractitionerRepository practitionerRepository, OrganizationRepository organizationRepository, PractitionerMapper practitionerMapper) {
        this.practitionerRepository = practitionerRepository;
        this.organizationRepository = organizationRepository;
        this.practitionerMapper = practitionerMapper;
    }

    private final PractitionerRepository practitionerRepository;
    private final OrganizationRepository organizationRepository;
    private final PractitionerMapper practitionerMapper;

    @Override
    @Transactional
    public PractitionerResponse createPractitiner(CreatePractitionerRequest request) {

        if(practitionerRepository.existsByPractitionerCode(request.practitionerCode())){
            throw new DuplicateResourceException(
                    "Practitioner code already exists: " +
                            request.practitionerCode()
            );
        }
        if(practitionerRepository.existsByLicenseNumber(request.licenseNumber())){
            throw new DuplicateResourceException(
                    "Practitioner Licence number already exist" + request.licenseNumber()
            );
        }
        Organization organization = organizationRepository
                .findById(request.organizationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with id: " + request.organizationId()
                ));

        Practitioner practitioner=practitionerMapper.toEntity(request,organization);
        Practitioner  savedpract=practitionerRepository.save(practitioner);

        return practitionerMapper.toResponse(savedpract);
    }

    @Override
    @Transactional(readOnly = true)
    public PractitionerResponse getPractitionerById(Long id) {
        Practitioner practitioner=practitionerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Practitioner not found with id: " + id));
        return practitionerMapper.toResponse(practitioner);
    }

    @Override
    @Transactional
    public Page<PractitionerResponse> getAllPractitioners(Pageable pageable) {
        return practitionerRepository.findAll(pageable).map(practitionerMapper::toResponse);

    }

    @Override
    @Transactional
    public PractitionerResponse updatePractitioner(Long id, UpdatePractitionerRequest request) {
        Practitioner practitioner;
        practitioner=practitionerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Practitioner not found with id: "+id));

        Organization organization = organizationRepository
                .findById(request.organizationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with id: " + request.organizationId()
                ));

        practitioner.setOrganization(organization);

        practitioner.setFirstName(request.firstName());
        practitioner.setLastName(request.lastName());
        practitioner.setGender(request.gender());
        practitioner.setDateOfBirth(request.dateOfBirth());
        practitioner.setSpecialization(request.specialization());
        practitioner.setQualification(request.qualification());
        practitioner.setPhone(request.phone());
        practitioner.setEmail(request.email());
        practitioner.setOrganization(organization);

        Practitioner savedpractitiner=practitionerRepository.save(practitioner);

        return practitionerMapper.toResponse(savedpractitiner);


    }
    @Override
    @Transactional
    public PractitionerResponse deactivatePractitioner(Long id) {

        Practitioner practitioner=practitionerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Practitioner Not found"+id));
        practitioner.setActive(false);

        return practitionerMapper.toResponse(practitioner);
    }

    @Override
    @Transactional
    public Page<PractitionerResponse> searchPractitioners(String search,String specialization,String gender,Long organizationId,Boolean active,Pageable pageable){
        Specification<Practitioner> spec = Specification.unrestricted();
        if(search !=null && !search.isBlank()){
            spec=spec.and(PractitionerSpecification.nameContains(search));
        }
        if(specialization !=null && !specialization.isBlank()){
            spec=spec.and(PractitionerSpecification.hasSpecialization(specialization));
        }
        if(gender !=null && !gender.isBlank()){
            spec=spec.and(PractitionerSpecification.hasGender(gender));
        }
        if(organizationId !=null){
            spec=spec.and(PractitionerSpecification.hasOrganizationId(organizationId));
        }
        if(active!=null){
            spec=spec.and(PractitionerSpecification.hasActive(active));
        }

        return practitionerRepository.findAll(spec,pageable).map(practitionerMapper::toResponse);


    }


}

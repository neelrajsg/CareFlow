package com.careflow.organization.service;

import com.careflow.common.exception.DuplicateResourceException;
import com.careflow.common.exception.ResourceNotFoundException;
import com.careflow.organization.dto.CreateOrganizationRequest;
import com.careflow.organization.dto.OrganizationResponse;
import com.careflow.organization.dto.UpdateOrganizationRequest;
import com.careflow.organization.entity.Organization;
import com.careflow.organization.mapper.OrganizationMapper;
import com.careflow.organization.repository.OrganizationRepository;
import com.careflow.organization.specification.OrganizationSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository orgRepo;
    private final OrganizationMapper orgMapper;

    public OrganizationServiceImpl(OrganizationRepository orgRepo, OrganizationMapper orgMapper) {
        this.orgRepo = orgRepo;
        this.orgMapper = orgMapper;
    }
    @Override
    @Transactional
    public OrganizationResponse createOrganization(CreateOrganizationRequest request) {
        if(orgRepo.existsByOrganizationCode(request.organizationCode())){
            throw new DuplicateResourceException(
                    "Organization Code Already Exists. " + request.organizationCode()
            );
        }

        Organization organization=orgMapper.toEntity(request);
        Organization savedOrganisation=orgRepo.save(organization);

        return orgMapper.toResponse(savedOrganisation);
    }

    @Override
    public OrganizationResponse getOrganizationById(Long id) {

        Organization organization=orgRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "Organization not found with id: " + id));
        return orgMapper.toResponse(organization);
    }

    @Override
    public Page<OrganizationResponse> findAllOrganization(Pageable pageable) {
        return orgRepo.findAll(pageable).map(orgMapper::toResponse);
    }

    @Override
    @Transactional
    public OrganizationResponse updateOrganization(Long id, UpdateOrganizationRequest request) {
        Organization updateOrg;
        updateOrg = orgRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "Organization not found with id: " + id));

        updateOrg.setName(request.name());
        updateOrg.setType(request.type());
        updateOrg.setPhone(request.phone());
        updateOrg.setEmail(request.email());
        updateOrg.setAddressLine1(request.addressLine1());
        updateOrg.setAddressLine2(request.addressLine2());
        updateOrg.setCity(request.city());
        updateOrg.setState(request.state());
        updateOrg.setPostalCode(request.postalCode());
        updateOrg.setCountry(request.country());

        Organization savedOrganization=orgRepo.save(updateOrg);

        return orgMapper.toResponse(savedOrganization);
    }

    @Override
    @Transactional
    public OrganizationResponse deactivatOrganization(Long id) {

        Organization organization = orgRepo.findById(id).orElseThrow(() -> {
                    return new ResourceNotFoundException(
                            "Organization not found with id: " + id
                    );
                });

        organization.setActive(false);

        return orgMapper.toResponse(organization);
    }

    @Override
    @Transactional
    public Page<OrganizationResponse> findOrganizationsByActive(Boolean active, Pageable pageable) {
        return orgRepo.findByActive(active, pageable).map(orgMapper::toResponse);
    }

    @Override
    @Transactional
    public Page<OrganizationResponse> searchOrganizations(String search, String city, String type, Boolean active, Pageable pageable) {
        Specification<Organization> spec = Specification.unrestricted();

        if(search !=null && !search.isBlank()){
            spec=spec.and(OrganizationSpecification.nameContains(search));
        }
        if(city !=null && !city.isBlank()){
            spec=spec.and(OrganizationSpecification.hasCity(city));
        }
        if(type !=null && !type.isBlank()){
            spec=spec.and(OrganizationSpecification.hasType(type));
        }
        if(active!=null){
            spec=spec.and(OrganizationSpecification.hasActive(active));
        }



        return orgRepo.findAll(spec,pageable).map(orgMapper::toResponse);
    }


}

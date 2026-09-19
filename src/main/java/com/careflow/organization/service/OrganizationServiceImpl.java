package com.careflow.organization.service;

import com.careflow.common.exception.DuplicateResourceException;
import com.careflow.common.exception.ResourceNotFoundException;
import com.careflow.organization.dto.CreateOrganizationRequest;
import com.careflow.organization.dto.OrganizationResponse;
import com.careflow.organization.entity.Organization;
import com.careflow.organization.mapper.OrganizationMapper;
import com.careflow.organization.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
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
    public List<OrganizationResponse> findAllOrganization() {
        return orgRepo.findAll().stream().map(orgMapper::toResponse).toList();
    }
}

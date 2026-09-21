package com.careflow.organization.service;

import com.careflow.organization.dto.CreateOrganizationRequest;
import com.careflow.organization.dto.OrganizationResponse;
import com.careflow.organization.dto.UpdateOrganizationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrganizationService {

    OrganizationResponse createOrganization(CreateOrganizationRequest request);
    OrganizationResponse getOrganizationById(Long id);
    Page<OrganizationResponse> findAllOrganization(Pageable pageable);
    OrganizationResponse updateOrganization(Long id, UpdateOrganizationRequest request);
    OrganizationResponse deactivatOrganization(Long id);
    Page<OrganizationResponse> findOrganizationsByActive(Boolean active,Pageable pageable);
    Page<OrganizationResponse> searchOrganizations(
            String search,
            String city,
            String type,
            Boolean active,
            Pageable pageable
    );
}

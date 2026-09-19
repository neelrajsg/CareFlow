package com.careflow.organization.service;

import com.careflow.organization.dto.CreateOrganizationRequest;
import com.careflow.organization.dto.OrganizationResponse;

import java.util.List;


public interface OrganizationService {

    OrganizationResponse createOrganization(CreateOrganizationRequest request);
    OrganizationResponse getOrganizationById(Long id);
    List<OrganizationResponse> findAllOrganization();
}

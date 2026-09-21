package com.careflow.organization.repository;

import com.careflow.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationRepository extends JpaRepository<Organization,Long>, JpaSpecificationExecutor<Organization> {
    boolean existsByOrganizationCode(String organizationCode);
    Page<Organization> findByActive(Boolean active, Pageable pageable);
}

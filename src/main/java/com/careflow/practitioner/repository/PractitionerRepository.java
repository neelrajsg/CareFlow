package com.careflow.practitioner.repository;

import com.careflow.practitioner.entity.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PractitionerRepository extends JpaRepository<Practitioner,Long>, JpaSpecificationExecutor<Practitioner> {
    boolean existsByPractitionerCode(String practitionerCode);

    boolean existsByLicenseNumber(String licenseNumber);
}

package com.careflow.practitioner.mapper;

import com.careflow.organization.entity.Organization;
import com.careflow.practitioner.dto.CreatePractitionerRequest;
import com.careflow.practitioner.dto.PractitionerResponse;
import com.careflow.practitioner.entity.Practitioner;
import org.springframework.stereotype.Component;

@Component
public class PractitionerMapper {

    public Practitioner toEntity(CreatePractitionerRequest request, Organization organization){
        Practitioner practitioner=new Practitioner();


        practitioner.setPractitionerCode(request.practitionerCode());
        practitioner.setFirstName(request.firstName());
        practitioner.setLastName(request.lastName());
        practitioner.setGender(request.gender());
        practitioner.setEmail(request.email());
        practitioner.setActive(true);
        practitioner.setDateOfBirth(request.dateOfBirth());
        practitioner.setSpecialization(request.specialization());
        practitioner.setQualification(request.qualification());
        practitioner.setLicenseNumber(request.licenseNumber());
        practitioner.setPhone(request.phone());
        practitioner.setOrganization(organization);

        return practitioner;
    }

    public PractitionerResponse toResponse(Practitioner practitioner){
        return  new PractitionerResponse(
                practitioner.getId(),
                practitioner.getPractitionerCode(),
                practitioner.getFirstName(),
                practitioner.getLastName(),
                practitioner.getGender(),
                practitioner.getDateOfBirth(),
                practitioner.getSpecialization(),
                practitioner.getQualification(),
                practitioner.getLicenseNumber(),
                practitioner.getPhone(),
                practitioner.getEmail(),

                practitioner.getOrganization().getId(),
                practitioner.getOrganization().getName(),

                practitioner.getActive(),
                practitioner.getCreatedAt(),
                practitioner.getUpdatedAt()
        );

    }


}

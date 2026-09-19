package com.careflow.organization.mapper;

import com.careflow.organization.dto.CreateOrganizationRequest;
import com.careflow.organization.dto.OrganizationResponse;
import com.careflow.organization.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {



    public Organization toEntity(CreateOrganizationRequest request){
        Organization org=new Organization();

        org.setOrganizationCode(request.organizationCode());
        org.setName(request.name());
        org.setType(request.type());
        org.setPhone(request.phone());
        org.setEmail(request.email());
        org.setAddressLine1(request.addressline1());
        org.setAddressLine2(request.addressline2());
        org.setCity(request.city());
        org.setState(request.state());
        org.setPostalCode(request.postalCode());
        org.setCountry(request.Country());
        org.setActive(true);

        return org;
    }
    public OrganizationResponse toResponse(Organization organization) {
        return new OrganizationResponse(
                organization.getId(),
                organization.getOrganizationCode(),
                organization.getName(),
                organization.getType(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getAddressLine1(),
                organization.getAddressLine2(),
                organization.getCity(),
                organization.getState(),
                organization.getPostalCode(),
                organization.getCountry(),
                organization.getActive(),
                organization.getCreatedAt(),
                organization.getUpdatedAt()
        );
    }

}

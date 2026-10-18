package com.careflow.patient.mapper;

import com.careflow.organization.entity.Organization;
import com.careflow.patient.dto.CreatePatientRequest;
import com.careflow.patient.dto.PatientResponse;
import com.careflow.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(CreatePatientRequest request, Organization organization) {
        if (request == null) {
            return null;
        }

        Patient patient = new Patient();

        // Identity & Tenancy
        patient.setPatientCode(request.patientCode());
        patient.setOrganization(organization);

        // Personal Information
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setGender(request.gender());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setBloodGroup(request.bloodGroup());

        // Contact Information
        patient.setPhone(request.phone());
        patient.setEmail(request.email());

        // Address
        patient.setAddressLine1(request.addressLine1());
        patient.setAddressLine2(request.addressLine2());
        patient.setCity(request.city());
        patient.setState(request.state());
        patient.setPostalCode(request.postalCode());
        patient.setCountry(request.country());

        // Emergency Contact
        patient.setEmergencyContactName(request.emergencyContactName());
        patient.setEmergencyContactPhone(request.emergencyContactPhone());

        return patient;
    }
    public PatientResponse toResponse(Patient patient) {
        if (patient == null) {
            return null;
        }

        Long organizationId = patient.getOrganization() != null ? patient.getOrganization().getId() : null;
        String organizationName = patient.getOrganization() != null ? patient.getOrganization().getName() : null;

        return new PatientResponse(
                patient.getId(),
                patient.getPatientCode(),          // or String.valueOf(patient.getId()) depending on your entity field
                patient.getFirstName(),
                patient.getLastName(),
                patient.getGender(),
                patient.getDateOfBirth(),
                patient.getBloodGroup(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getAddressLine1(),
                patient.getAddressLine2(),
                patient.getCity(),
                patient.getState(),
                patient.getPostalCode(),
                patient.getCountry(),
                patient.getEmergencyContactName(),
                patient.getEmergencyContactPhone(),
                organizationId,
                organizationName,
                patient.getActive(),
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }
}
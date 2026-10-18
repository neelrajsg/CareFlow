package com.careflow.patient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResponse(
        Long id,
        String patientCode,
        String firstName,
        String lastName,
        String gender,
        LocalDate dateOfBirth,
        String bloodGroup,
        String phone ,
        String email ,
        String addressLine1   ,
        String addressLine2 ,
        String  city       ,
        String  state       ,
        String  postalCode  ,
        String  country         ,
        String emergencyContactName ,
        String  emergencyContactPhone ,
        Long organizationId,
        String organizationName,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}

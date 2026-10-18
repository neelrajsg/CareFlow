package com.careflow.practitioner.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PractitionerResponse(
        Long id,
        String practitionerCode,
        String firstName,
        String lastName,
        String gender,
        LocalDate dateOfBirth,
        String specialization,
        String qualification,
        String licenseNumber,
        String  phone,
        String email,
        Long organizationId,
        String organizationName,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

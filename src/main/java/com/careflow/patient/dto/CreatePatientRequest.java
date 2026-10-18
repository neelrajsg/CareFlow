package com.careflow.patient.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreatePatientRequest(

        @NotBlank(message = "Patient code is required")
        @Size(max = 50)
        String patientCode,

        @NotBlank(message = "First name is required")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100)
        String lastName,

        @NotBlank(message = "Gender cannot be Blank")
        @Size(max = 20)
        String gender,
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,
        @Size(max = 10)
        String bloodGroup,
        @Size(max=20)
        String phone ,
        @Email
        @Size(max=150)
        String email,
        @Size(max=255)
        String addressLine1,
        @Size(max=255)
        String addressLine2,
        @Size(max=100)
        String city,
        @Size(max=100)
        String state,
        @Size(max = 20)
        String postalCode,
        @Size(max=100)
        String country,
        @Size(max=150)
        String emergencyContactName,
        @Size(max=20)
        String emergencyContactPhone,
        @NotNull
        Long organizationId

) {
}

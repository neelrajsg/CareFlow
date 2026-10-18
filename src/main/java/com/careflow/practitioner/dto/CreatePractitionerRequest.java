package com.careflow.practitioner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreatePractitionerRequest(
        @NotBlank(message = "Practitioner code cannot be blank")
        @Size(max = 50)
        String practitionerCode,

        @NotBlank(message = "First name cannot be blank")
        @Size(max = 100)
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Size(max = 100)
        String lastName,
        @Size(max = 20)
        String gender,
        LocalDate dateOfBirth,
        @Size(max = 100)
        String specialization,
        @Size(max = 150)
        String qualification,
        @NotBlank(message = "licence Number cannot be blank")
        @Size(max = 100)
        String licenseNumber,
        @Size(max = 20)
        String phone,
        @Email(message = "Email should be valid")
        @Size(max =150)
        String email,
        @NotNull(message = "Organization cannot be blank")
        Long  organizationId

) {
}

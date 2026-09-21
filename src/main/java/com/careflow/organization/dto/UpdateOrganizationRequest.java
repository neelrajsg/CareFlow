package com.careflow.organization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateOrganizationRequest(

        @NotBlank(message = "Organization name is required")
        @Size(max = 150)
        String name,

        @NotBlank(message = "Organization type is required")
        @Size(max = 50)
        String type,

        @Size(max = 20)
        String phone,

        @Email(message = "Email must be valid")
        @Size(max = 150)
        String email,

        @Size(max = 255)
        String addressLine1,

        @Size(max = 255)
        String addressLine2,

        @Size(max = 100)
        String city,

        @Size(max = 100)
        String state,

        @Size(max = 20)
        String postalCode,

        @Size(max = 100)
        String country
) {
}

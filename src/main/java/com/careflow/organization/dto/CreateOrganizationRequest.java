package com.careflow.organization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrganizationRequest(

    @NotBlank(message = "Organization Code cannot be Blank")
    @Size(max = 50,message = "Organization code cannot exceed 50 characters")
    String organizationCode,

    @NotBlank(message = "Organization name is required")
    @Size(max = 150,message =  "Organization name cannot exceed 150 characters")
    String name,

    @NotBlank(message = "Organization type is required")
    @Size(max = 50)
    String type,

    @Size(max = 50)
    String phone,

    @Size(max = 150)
    @Email(message = "Email Must be Valid")
    String email,

    @Size(max = 255)
    String addressline1,

    @Size(max = 255)
    String addressline2,

    @Size(max = 100)
    String city,

    @Size(max = 100)
    String state,

    @Size(max = 20)
    String postalCode,

    @Size(max = 100)
    String Country

    ){}

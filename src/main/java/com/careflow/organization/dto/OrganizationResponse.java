package com.careflow.organization.dto;

import java.time.LocalDateTime;

public record OrganizationResponse(
        Long id,
        String organizationCode,
        String name,
        String type,
        String phone,
        String email,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

package com.careflow.patient.specification;

import com.careflow.patient.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class PatientSpecification {
    private PatientSpecification(){}

    public static Specification<Patient> hasActive(Boolean active){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"),active));
    }
    public static Specification<Patient> hasOrganizationId(Long organizationId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("organization").get("id"),
                        organizationId
                );
    }
    public static Specification<Patient> nameContains(String search) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + search.toLowerCase(Locale.ROOT) + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("firstName")),
                            pattern
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("lastName")),
                            pattern
                    )
            );
        };
    }

}

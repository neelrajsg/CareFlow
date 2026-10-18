package com.careflow.practitioner.specification;

import com.careflow.practitioner.entity.Practitioner;
import org.springframework.data.jpa.domain.Specification;

public class PractitionerSpecification {

    private PractitionerSpecification() {
    }

    public static Specification<Practitioner> hasSpecialization(
            String specialization) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("specialization")),
                        specialization.toLowerCase()
                );
    }


    public static Specification<Practitioner> nameContains(
            String search) {

        return (root, query, criteriaBuilder) -> {

            String pattern = "%" + search.toLowerCase() + "%";

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


    public static Specification<Practitioner> hasGender(
            String gender) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("gender")),
                        gender.toLowerCase()
                );
    }


    public static Specification<Practitioner> hasOrganizationId(
            Long organizationId) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("organization").get("id"),
                        organizationId
                );
    }


    public static Specification<Practitioner> hasActive(
            Boolean active) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("active"),
                        active
                );
    }
}
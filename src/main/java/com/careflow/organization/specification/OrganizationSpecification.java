package com.careflow.organization.specification;

import com.careflow.organization.entity.Organization;
import org.springframework.data.jpa.domain.Specification;

public class OrganizationSpecification {

    // Prevent object creation because all methods are static
    private OrganizationSpecification(){

    }

    //Filtering by city
    public static Specification<Organization> hasCity(String city){
        return(root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.lower(root.get("city")),
                city.toLowerCase()
        );
    }

    //Filtering by Organization Type
    public static Specification<Organization> hasType(String type){
        return((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.lower(root.get("type")),type.toLowerCase()
        ));

    }

    //Filtering By Organization Active
    public static Specification<Organization> hasActive(Boolean active) {

        return (root, query, criteriaBuilder) ->criteriaBuilder.equal(root.get("active"),active);
    }

    //Search By Organization Name

    public static Specification<Organization> nameContains(String search){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),"%"+ search.toLowerCase() +"%" );
    }

}

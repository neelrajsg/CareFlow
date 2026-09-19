package com.careflow.organization.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;

    @Column(name = "organization_code", nullable = false, unique = true, length = 50)
    private String organizationCode;

    @Column(name = "name",nullable = false,unique = false,length = 150)
    private String name;

    @Column(name = "type",nullable = false,length = 50)
    private String type;
    @Column(name = "phone",length = 20)
    private String phone ;
    @Column(name = "email",length = 150)
    private String email;
    @Column(name = "address_line_1",length = 255)
    private String addressLine1;
    @Column(name= "address_line_2",length = 255)
    private String addressLine2;
    @Column(name = "city",length = 100)
    private String city;
    @Column(name = "state",length = 100)
    private String state;
    @Column(name = "postal_code",length = 20)
    private String postalCode;
    @Column(name = "country",length = 100)
    private String country;
    @Column(name = "active",nullable = false)
    private Boolean active=true;
    @Timestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @Timestamp
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;



}

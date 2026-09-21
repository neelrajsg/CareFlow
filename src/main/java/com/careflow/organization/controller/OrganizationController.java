package com.careflow.organization.controller;

import com.careflow.organization.dto.CreateOrganizationRequest;
import com.careflow.organization.dto.OrganizationResponse;
import com.careflow.organization.dto.UpdateOrganizationRequest;
import com.careflow.organization.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(
            @Valid  @RequestBody CreateOrganizationRequest request
            ){
        OrganizationResponse response=organizationService.createOrganization(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable long id){
        OrganizationResponse response=organizationService.getOrganizationById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrganizationResponse>> findAllOrganization(Pageable pageable){
        Page<OrganizationResponse> response=organizationService.findAllOrganization(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationResponse> updateOrganization(@PathVariable long id, @Valid @RequestBody UpdateOrganizationRequest request){
        OrganizationResponse response=organizationService.updateOrganization(id,request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<OrganizationResponse> deactivateOrganization(@PathVariable Long id){
        OrganizationResponse response=organizationService.deactivatOrganization(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<OrganizationResponse>> findOrganizationsByActive(@RequestParam Boolean value, Pageable pageable) {
        Page<OrganizationResponse> response = organizationService.findOrganizationsByActive(value, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrganizationResponse>> searchOrganizations(

            @RequestParam(required = false) String search,

            @RequestParam(required = false) String city,

            @RequestParam(required = false) String type,

            @RequestParam(required = false) Boolean active,

            Pageable pageable) {

        Page<OrganizationResponse> response=organizationService.searchOrganizations(search, city, type, active, pageable);
        return ResponseEntity.ok(response);

        // YOU WRITE THIS PART

    }

}

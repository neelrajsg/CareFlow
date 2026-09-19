package com.careflow.organization.controller;

import com.careflow.organization.dto.CreateOrganizationRequest;
import com.careflow.organization.dto.OrganizationResponse;
import com.careflow.organization.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/All")
    public ResponseEntity<List<OrganizationResponse>> findAllOrganization(){
        List<OrganizationResponse> response=organizationService.findAllOrganization();
        return ResponseEntity.ok(response);
    }



}

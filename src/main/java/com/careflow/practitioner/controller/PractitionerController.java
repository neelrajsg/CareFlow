package com.careflow.practitioner.controller;

import com.careflow.practitioner.dto.CreatePractitionerRequest;
import com.careflow.practitioner.dto.PractitionerResponse;
import com.careflow.practitioner.dto.UpdatePractitionerRequest;
import com.careflow.practitioner.service.PractitionerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/practitioners")
public class PractitionerController {

    private final PractitionerService practitionerService;

    public PractitionerController(PractitionerService practitionerService) {
        this.practitionerService = practitionerService;
    }

    @PostMapping
    public ResponseEntity<PractitionerResponse> createPractitioner(@Valid @RequestBody CreatePractitionerRequest request){

        PractitionerResponse response=practitionerService.createPractitiner(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PractitionerResponse> getPractitionerById(@PathVariable Long id){
        PractitionerResponse response=practitionerService.getPractitionerById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PractitionerResponse>> getAllPractitioners(Pageable pageable){
        Page<PractitionerResponse> response=practitionerService.getAllPractitioners(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PractitionerResponse> updatePractitioner(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePractitionerRequest request) {

        PractitionerResponse response =
                practitionerService.updatePractitioner(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PractitionerResponse> deactivatePractitioner(@PathVariable Long id){
        PractitionerResponse response=practitionerService.deactivatePractitioner(id);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PractitionerResponse>> searchPractitioners(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Long organizationId,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {

        Page<PractitionerResponse> response =practitionerService.searchPractitioners(
                        search,
                        specialization,
                        gender,
                        organizationId,
                        active,
                        pageable
                );

        return ResponseEntity.ok(response);
    }

}

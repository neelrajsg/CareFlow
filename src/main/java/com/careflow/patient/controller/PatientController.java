package com.careflow.patient.controller;

import com.careflow.patient.dto.CreatePatientRequest;
import com.careflow.patient.dto.PatientResponse;
import com.careflow.patient.dto.UpdatePatientRequest;
import com.careflow.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;


    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody CreatePatientRequest request){
        PatientResponse response=patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id){
        PatientResponse response=patientService.getPatientById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getAllPatients(
            Pageable pageable) {

        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PatientResponse> deactivatePatient(@PathVariable Long id){
        return  ResponseEntity.ok(patientService.deactivatePatient(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @Valid @RequestBody UpdatePatientRequest request){

        PatientResponse response=patientService.updatePatient(id,request);
        return ResponseEntity.ok(response);

    }
    @GetMapping("/search")
    public ResponseEntity<Page<PatientResponse>> searchPatients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long organizationId,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {

        Page<PatientResponse> response = patientService.searchPatients(
                search,
                organizationId,
                active,
                pageable
        );

        return ResponseEntity.ok(response);
    }

}

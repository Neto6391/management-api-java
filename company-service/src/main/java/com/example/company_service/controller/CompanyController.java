package com.example.company_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.company_service.dto.CompanyDto;
import com.example.company_service.service.CompanyService;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto response = companyService.createCompany(companyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable UUID companyId) {
        return ResponseEntity.ok(companyService.getCompany(companyId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable UUID companyId,
            @RequestBody CompanyDto companyDto) {
        CompanyDto response = companyService.updateCompany(companyId,
                companyDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable UUID companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }
}

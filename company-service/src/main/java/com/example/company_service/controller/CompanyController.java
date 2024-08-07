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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Operation(summary = "Create a new company", description = "Creates a new company with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(
            @Parameter(description = "Company data to be created", required = true) @RequestBody CompanyDto companyDto) {
        CompanyDto response = companyService.createCompany(companyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a company by ID", description = "Fetches a company by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getCompany(
            @Parameter(description = "ID of the company to be fetched", required = true) @PathVariable UUID companyId) {
        return ResponseEntity.ok(companyService.getCompany(companyId));
    }

    @Operation(summary = "Get all companies", description = "Fetches a list of all companies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of companies retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDto.class))),
    })
    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @Operation(summary = "Update a company", description = "Updates an existing company with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDto> updateCompany(
            @Parameter(description = "ID of the company to be updated", required = true) @PathVariable UUID companyId,
            @Parameter(description = "Updated company data", required = true) @RequestBody CompanyDto companyDto) {
        CompanyDto response = companyService.updateCompany(companyId, companyDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a company", description = "Deletes a company by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(
            @Parameter(description = "ID of the company to be deleted", required = true) @PathVariable UUID companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }
}

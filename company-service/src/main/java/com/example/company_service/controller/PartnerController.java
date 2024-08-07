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

import com.example.company_service.dto.PartnerDto;
import com.example.company_service.service.PartnerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/partners")
public class PartnerController {
    @Autowired
    private PartnerService partnerService;

    @Operation(summary = "Create a new partner", description = "Creates a new partner with id company and the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Partner created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PartnerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @PostMapping("/{companyId}")
    public ResponseEntity<PartnerDto> createPartner(
            @Parameter(description = "Partner data to be created", required = true) @RequestBody PartnerDto partnerDto,
            @Parameter(description = "ID of the company to be fetched", required = true) @PathVariable UUID companyId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.createPartner(partnerDto,
                companyId));
    }

    @Operation(summary = "Get a partner by ID", description = "Fetches a partner by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partner found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PartnerDto.class))),
            @ApiResponse(responseCode = "404", description = "Partner not found", content = @Content)
    })
    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerDto> getPartner(
            @Parameter(description = "ID of the partner to be fetched", required = true) @PathVariable UUID partnerId) {
        return ResponseEntity.ok(partnerService.getPartnerById(partnerId));
    }

    @Operation(summary = "Get all partners", description = "Fetches a list of all partners.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of companies retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PartnerDto.class))),
    })
    @GetMapping
    public ResponseEntity<List<PartnerDto>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @Operation(summary = "Update a partner", description = "Updates an existing partner with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partner updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PartnerDto.class))),
            @ApiResponse(responseCode = "404", description = "Partner not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PutMapping("/{partnerId}")
    public ResponseEntity<PartnerDto> updatePartner(
            @Parameter(description = "ID of the partner to be updated", required = true) @PathVariable UUID partnerId,
            @Parameter(description = "Updated partner data", required = true) @RequestBody PartnerDto partnerDto) {
        return ResponseEntity.ok(partnerService.updatePartner(partnerId,
                partnerDto));
    }

    @Operation(summary = "Delete a Partner", description = "Deletes a partner by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Partner deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Partner not found", content = @Content)
    })
    @DeleteMapping("/{partnerId}")
    public ResponseEntity<Void> deletePartner(@PathVariable UUID partnerId) {
        partnerService.deletePartner(partnerId);
        return ResponseEntity.noContent().build();
    }
}

package com.example.company_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/v1/partners")
public class PartnerController {
    @Autowired
    private PartnerService partnerService;

    @PostMapping("/{companyId}")
    public ResponseEntity<PartnerDto> createPartner(@RequestBody PartnerDto partnerDto, @PathVariable UUID companyId) {
        return ResponseEntity.ok(partnerService.createPartner(partnerDto,
                companyId));
    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<PartnerDto> getPartner(@PathVariable UUID partnerId) {
        return ResponseEntity.ok(partnerService.getPartnerById(partnerId));
    }

    @GetMapping
    public ResponseEntity<List<PartnerDto>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @PutMapping("/{partnerId}")
    public ResponseEntity<PartnerDto> updatePartner(@PathVariable UUID partnerId,
            @RequestBody PartnerDto partnerDto) {
        return ResponseEntity.ok(partnerService.updatePartner(partnerId,
                partnerDto));
    }

    @DeleteMapping("/{partnerId}")
    public ResponseEntity<Void> deletePartner(@PathVariable UUID partnerId) {
        partnerService.deletePartner(partnerId);
        return ResponseEntity.noContent().build();
    }
}

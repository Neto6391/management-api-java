package com.example.company_service.service;

import java.util.List;
import java.util.UUID;

import com.example.company_service.dto.PartnerDto;

public interface PartnerService {
    PartnerDto createPartner(PartnerDto partner, UUID companyId);

    List<PartnerDto> getAllPartners();

    PartnerDto getPartnerById(UUID id);

    PartnerDto updatePartner(UUID id, PartnerDto partner);

    void deletePartner(UUID id);
}

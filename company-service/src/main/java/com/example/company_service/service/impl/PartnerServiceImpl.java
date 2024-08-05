package com.example.company_service.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.company_service.dto.PartnerDto;
import com.example.company_service.entity.Company;
import com.example.company_service.entity.Partner;
import com.example.company_service.exception.ResourceNotFoundException;
import com.example.company_service.mapper.PartnerMapper;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.repository.PartnerRepository;
import com.example.company_service.service.PartnerService;

public class PartnerServiceImpl implements PartnerService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerMapper partnerMapper;

    @Override
    public PartnerDto createPartner(PartnerDto partnerDto, UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Partner partner = partnerMapper.toEntity(partnerDto);
        partner.setCompany(company);
        partner = partnerRepository.save(partner);
        return partnerMapper.toDto(partner);
    }

    @Override
    public PartnerDto getPartnerById(UUID id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));
        return partnerMapper.toDto(partner);
    }

    @Override
    public List<PartnerDto> getAllPartners() {
        return partnerRepository.findAll().stream()
                .map(partnerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PartnerDto updatePartner(UUID partnerId, PartnerDto partnerDto) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));
        partnerMapper.toEntity(partnerDto);
        partner = partnerRepository.save(partner);
        return partnerMapper.toDto(partner);
    }

    @Override
    public void deletePartner(UUID partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));
        partnerRepository.delete(partner);
    }
}

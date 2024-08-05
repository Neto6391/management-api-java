package com.example.company_service.service;

import java.util.List;
import java.util.UUID;

import com.example.company_service.dto.CompanyDto;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto);

    CompanyDto getCompany(UUID companyId);

    List<CompanyDto> getAllCompanies();

    CompanyDto updateCompany(UUID companyId, CompanyDto companyDto);

    void deleteCompany(UUID companyId);
}

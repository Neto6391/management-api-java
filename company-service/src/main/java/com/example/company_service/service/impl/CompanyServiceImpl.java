package com.example.company_service.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.company_service.dto.CompanyDto;
import com.example.company_service.entity.Company;
import com.example.company_service.exception.ResourceNotFoundException;
import com.example.company_service.mapper.CompanyMapper;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.service.CompanyService;

public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public CompanyDto createCompany(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Override
    public CompanyDto getCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        return companyMapper.toDto(company);
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto updateCompany(UUID companyId, CompanyDto companyDto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        companyMapper.toEntity(companyDto);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Override
    public void deleteCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        companyRepository.delete(company);
    }
}

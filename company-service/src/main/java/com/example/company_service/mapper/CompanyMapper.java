package com.example.company_service.mapper;

import org.mapstruct.Mapper;

import com.example.company_service.dto.CompanyDto;
import com.example.company_service.entity.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);
}

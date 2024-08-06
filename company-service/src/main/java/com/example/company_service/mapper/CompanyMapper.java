package com.example.company_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.company_service.dto.CompanyDto;
import com.example.company_service.entity.Company;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {

    public static final CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    public abstract CompanyDto toDto(Company company);

    public abstract Company toEntity(CompanyDto companyDto);
}
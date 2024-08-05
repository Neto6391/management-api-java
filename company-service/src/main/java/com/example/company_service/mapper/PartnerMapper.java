package com.example.company_service.mapper;

import org.mapstruct.Mapper;

import com.example.company_service.dto.PartnerDto;
import com.example.company_service.entity.Partner;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    PartnerDto toDto(Partner partner);

    Partner toEntity(PartnerDto partnerDto);
}

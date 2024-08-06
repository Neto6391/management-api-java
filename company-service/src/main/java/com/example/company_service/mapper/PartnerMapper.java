package com.example.company_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.company_service.dto.PartnerDto;
import com.example.company_service.entity.Partner;

@Mapper(componentModel = "spring")
public abstract class PartnerMapper {

    public static final PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);

    public abstract PartnerDto toDto(Partner partner);

    public abstract Partner toEntity(PartnerDto partnerDto);
}
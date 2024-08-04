package com.example.company_service.dto;

import java.util.List;
import java.util.UUID;

public class CompanyDto {
    private UUID id;
    private String name;
    private String cnpj;
    private List<PartnerDto> partners;

    public CompanyDto() {
        this.id = UUID.randomUUID();
    }

    public CompanyDto(String name, String cnpj) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cnpj = cnpj;
    }

    public UUID getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setPartners(PartnerDto partner) {
        this.partners.add(partner);
    }

    public List<PartnerDto> getPartners() {
        return this.partners;
    }
}

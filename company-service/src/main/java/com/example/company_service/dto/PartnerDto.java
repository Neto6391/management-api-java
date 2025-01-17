package com.example.company_service.dto;

import java.util.UUID;

public class PartnerDto {
    private UUID id;
    private String name;
    private String email;
    private CompanyDto company;

    public PartnerDto() {
    }

    public PartnerDto(UUID id, String name, String email, CompanyDto company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.company = company;
    }

    public PartnerDto(String name, String email, CompanyDto company) {
        this.name = name;
        this.email = email;
        this.company = company;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public CompanyDto getCompany() {
        return this.company;
    }

}

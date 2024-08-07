package com.example.company_service.dto;

import java.time.Instant;
import java.util.UUID;

public class CompanyDto {
    private UUID id;
    private String name;
    private String cnpj;
    private Instant createdAt;
    private Instant updatedAt;

    public CompanyDto() {
    }

    public CompanyDto(UUID id, String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }

    public CompanyDto(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
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

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

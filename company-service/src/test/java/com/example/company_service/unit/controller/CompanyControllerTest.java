package com.example.company_service.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.company_service.controller.CompanyController;
import com.example.company_service.dto.CompanyDto;
import com.example.company_service.service.CompanyService;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyControllerTest {
    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private CompanyDto companyDto;
    private UUID companyId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        companyId = UUID.randomUUID();
        companyDto = new CompanyDto(companyId, "Test Company", "123123123213");
    }

    @Test
    public void whenCreateCompany_thenReturnCompany() {
        when(companyService.createCompany(any(CompanyDto.class))).thenReturn(companyDto);

        ResponseEntity<CompanyDto> response = companyController.createCompany(companyDto);

        assertThat(response.getBody()).isEqualTo(companyDto);
        verify(companyService, times(1)).createCompany(any(CompanyDto.class));
    }

    @Test
    public void whenGetCompany_thenReturnCompany() {
        when(companyService.getCompany(companyId)).thenReturn(companyDto);

        ResponseEntity<CompanyDto> response = companyController.getCompany(companyId);

        assertThat(response.getBody()).isEqualTo(companyDto);
        verify(companyService, times(1)).getCompany(companyId);
    }

    @Test
    public void whenGetAllCompanies_thenReturnCompaniesList() {
        List<CompanyDto> companies = List.of(companyDto);
        when(companyService.getAllCompanies()).thenReturn(companies);

        ResponseEntity<List<CompanyDto>> response = companyController.getAllCompanies();

        assertThat(response.getBody()).isEqualTo(companies);
        verify(companyService, times(1)).getAllCompanies();
    }

    @Test
    public void whenUpdateCompany_thenReturnUpdatedCompany() {
        when(companyService.updateCompany(companyId, companyDto)).thenReturn(companyDto);

        ResponseEntity<CompanyDto> response = companyController.updateCompany(companyId, companyDto);

        assertThat(response.getBody()).isEqualTo(companyDto);
        verify(companyService, times(1)).updateCompany(companyId, companyDto);
    }

    @Test
    public void whenDeleteCompany_thenVerifyDeletion() {
        doNothing().when(companyService).deleteCompany(companyId);

        ResponseEntity<Void> response = companyController.deleteCompany(companyId);

        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.noContent().build().getStatusCode());
        verify(companyService, times(1)).deleteCompany(companyId);
    }
}

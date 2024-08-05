package com.example.company_service.unit.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.example.company_service.dto.CompanyDto;
import com.example.company_service.entity.Company;
import com.example.company_service.exception.ResourceNotFoundException;
import com.example.company_service.mapper.CompanyMapper;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.service.impl.CompanyServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    private Company company;
    private CompanyDto companyDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        company = new Company("Test Company", "123123123213");
        companyDto = new CompanyDto(company.getId(), company.getName(), company.getCnpj());
    }

    @Test
    @DisplayName("Should create a new company")
    public void shouldCreateCompany() {
        when(companyMapper.toEntity(companyDto)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toDto(company)).thenReturn(companyDto);

        CompanyDto createdCompany = companyService.createCompany(companyDto);

        assertThat(createdCompany).isNotNull();
        assertThat(createdCompany.getName()).isEqualTo(company.getName());
    }

    @Test
    @DisplayName("Should find an existing company")
    public void shouldFindCompany() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
        when(companyMapper.toDto(company)).thenReturn(companyDto);

        CompanyDto foundCompany = companyService.getCompany(company.getId());

        assertThat(foundCompany).isNotNull();
        assertThat(foundCompany.getName()).isEqualTo(company.getName());
    }

    @Test
    @DisplayName("Should get a list of all companies")
    public void shouldGetAllCompanies() {
        when(companyRepository.findAll()).thenReturn(Collections.singletonList(company));
        when(companyMapper.toDto(company)).thenReturn(companyDto);

        List<CompanyDto> companies = companyService.getAllCompanies();

        assertThat(companies).isNotEmpty();
        assertThat(companies.size()).isEqualTo(1);
        assertThat(companies.get(0).getName()).isEqualTo(company.getName());
    }

    @Test
    @DisplayName("Should update an existing company")
    public void shouldUpdateCompany() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);
        when(companyMapper.toDto(company)).thenReturn(companyDto);
        companyDto.setName("another name");

        CompanyDto updatedCompany = companyService.updateCompany(company.getId(), companyDto);
        assertThat(updatedCompany).isNotNull();
        assertThat(updatedCompany.getName()).isEqualTo(companyDto.getName());
    }

    @Test
    @DisplayName("Should delete an existing company")
    public void shouldDeleteCompany() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));
        doNothing().when(companyRepository).delete(company);

        companyService.deleteCompany(company.getId());

        verify(companyRepository, times(1)).delete(company);
    }

    @Test
    @DisplayName("Should throw an exception when company not found")
    public void shouldThrowExceptionWhenGettingNonExistingCompany() {
        UUID nonExistingId = UUID.randomUUID();
        when(companyRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.getCompany(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Company not found");
    }
}

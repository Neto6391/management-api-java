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
import com.example.company_service.dto.PartnerDto;
import com.example.company_service.entity.Company;
import com.example.company_service.entity.Partner;
import com.example.company_service.exception.ResourceNotFoundException;
import com.example.company_service.mapper.PartnerMapper;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.repository.PartnerRepository;
import com.example.company_service.service.impl.CompanyServiceImpl;
import com.example.company_service.service.impl.PartnerServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
public class PartnerServiceTest {
    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PartnerMapper partnerMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    private Company company;
    private Partner partner;

    private CompanyDto companyDto;
    private PartnerDto partnerDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        company = new Company("Test Company", "123123123213");
        partner = new Partner("Test Partner", "email@example.com", company);
        companyDto = new CompanyDto(company.getId(), company.getName(), company.getCnpj());
        partnerDto = new PartnerDto(partner.getId(), partner.getName(), partner.getEmail(), companyDto);
    }

    @Test
    @DisplayName("Should create a new partner")
    public void shouldCreatePartner() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        when(partnerMapper.toEntity(partnerDto)).thenReturn(partner);
        when(partnerRepository.save(partner)).thenReturn(partner);
        when(partnerMapper.toDto(partner)).thenReturn(partnerDto);

        PartnerDto createdPartner = partnerService.createPartner(partnerDto, company.getId());

        assertThat(createdPartner).isNotNull();
        assertThat(createdPartner.getName()).isEqualTo(partner.getName());
    }

    @Test
    @DisplayName("Should find an existing partner")
    public void shouldFindPartner() {
        when(partnerRepository.findById(partner.getId())).thenReturn(Optional.of(partner));
        when(partnerMapper.toDto(partner)).thenReturn(partnerDto);

        PartnerDto foundPartner = partnerService.getPartnerById(partner.getId());

        assertThat(foundPartner).isNotNull();
        assertThat(foundPartner.getName()).isEqualTo(partner.getName());
    }

    @Test
    @DisplayName("Should get a list of all partners")
    public void shouldGetAllPartners() {
        when(partnerRepository.findAll()).thenReturn(Collections.singletonList(partner));
        when(partnerMapper.toDto(partner)).thenReturn(partnerDto);

        List<PartnerDto> partners = partnerService.getAllPartners();

        assertThat(partners).isNotEmpty();
        assertThat(partners.size()).isEqualTo(1);
        assertThat(partners.get(0).getName()).isEqualTo(partner.getName());
    }

    @Test
    @DisplayName("Should update an existing partner")
    public void shouldUpdatePartner() {
        when(partnerRepository.findById(partner.getId())).thenReturn(Optional.of(partner));
        when(partnerRepository.save(partner)).thenReturn(partner);
        when(partnerMapper.toDto(partner)).thenReturn(partnerDto);
        partnerDto.setName("another name partner");

        PartnerDto updatedPartner = partnerService.updatePartner(partner.getId(), partnerDto);
        assertThat(updatedPartner).isNotNull();
        assertThat(updatedPartner.getName()).isEqualTo(partnerDto.getName());
    }

    @Test
    @DisplayName("Should delete an existing partner")
    public void shouldDeletePartner() {
        when(partnerRepository.findById(partner.getId())).thenReturn(Optional.of(partner));
        doNothing().when(partnerRepository).delete(partner);

        partnerService.deletePartner(partner.getId());

        verify(partnerRepository, times(1)).delete(partner);
    }

    @Test
    @DisplayName("Should throw an exception when partner not found")
    public void shouldThrowExceptionWhenPartnerNotFound() {
        UUID nonExistingId = UUID.randomUUID();
        when(partnerRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> partnerService.getPartnerById(nonExistingId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Partner not found");
    }

}

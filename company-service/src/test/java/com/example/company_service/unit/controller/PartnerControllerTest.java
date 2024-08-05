package com.example.company_service.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.company_service.controller.PartnerController;
import com.example.company_service.dto.CompanyDto;
import com.example.company_service.dto.PartnerDto;
import com.example.company_service.service.PartnerService;

import static org.assertj.core.api.Assertions.assertThat;

public class PartnerControllerTest {
    @Mock
    private PartnerService partnerService;

    @InjectMocks
    private PartnerController partnerController;

    private PartnerDto partnerDto;
    private CompanyDto companyDto;
    private UUID partnerId;
    private UUID companyId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        partnerId = UUID.randomUUID();
        companyId = UUID.randomUUID();
        companyDto = new CompanyDto(companyId, "Test Company", "123123123213");
        partnerDto = new PartnerDto(partnerId, "Test Partner", "test@example.com", companyDto);
    }

    @Test
    @DisplayName("Should create a new partner and return it")
    public void shouldCreateNewPartnerAndReturnIt() {
        when(partnerService.createPartner(any(PartnerDto.class), eq(companyId))).thenReturn(partnerDto);

        ResponseEntity<PartnerDto> response = partnerController.createPartner(partnerDto, companyId);

        assertThat(response.getBody()).isEqualTo(partnerDto);
        verify(partnerService, times(1)).createPartner(any(PartnerDto.class), eq(companyId));
    }

    @Test
    @DisplayName("Should find and return an existing partner")
    public void shouldFindAndReturnExistingPartner() {
        when(partnerService.getPartnerById(partnerId)).thenReturn(partnerDto);

        ResponseEntity<PartnerDto> response = partnerController.getPartner(partnerId);

        assertThat(response.getBody()).isEqualTo(partnerDto);
        verify(partnerService, times(1)).getPartnerById(partnerId);
    }

    @Test
    @DisplayName("Should return a list of all partners")
    public void shouldReturnListOfAllPartners() {
        List<PartnerDto> partners = List.of(partnerDto);
        when(partnerService.getAllPartners()).thenReturn(partners);

        ResponseEntity<List<PartnerDto>> response = partnerController.getAllPartners();

        assertThat(response.getBody()).isEqualTo(partners);
        verify(partnerService, times(1)).getAllPartners();
    }

    @Test
    @DisplayName("Should update an existing partner and return the updated version")
    public void shouldUpdateExistingPartnerAndReturnUpdatedVersion() {
        when(partnerService.updatePartner(partnerId, partnerDto)).thenReturn(partnerDto);

        ResponseEntity<PartnerDto> response = partnerController.updatePartner(partnerId, partnerDto);

        assertThat(response.getBody()).isEqualTo(partnerDto);
        verify(partnerService, times(1)).updatePartner(partnerId, partnerDto);
    }

    @Test
    @DisplayName("Should verify partner deletion")
    public void shouldVerifyPartnerDeletion() {
        doNothing().when(partnerService).deletePartner(partnerId);

        ResponseEntity<Void> response = partnerController.deletePartner(partnerId);

        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.noContent().build().getStatusCode());
        verify(partnerService, times(1)).deletePartner(partnerId);
    }
}

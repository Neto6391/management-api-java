package com.example.company_service.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.company_service.controller.PartnerController;
import com.example.company_service.dto.CompanyDto;
import com.example.company_service.dto.PartnerDto;
import com.example.company_service.entity.Company;
import com.example.company_service.entity.Partner;
import com.example.company_service.mapper.PartnerMapper;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.repository.PartnerRepository;
import com.example.company_service.service.PartnerService;

@WebMvcTest(PartnerController.class)
public class PartnerControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private PartnerRepository partnerRepository;

    @Spy
    private PartnerService partnerService;

    @Mock
    private PartnerMapper partnerMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Company company = new Company("Test Company", "123123123123");
        Partner partner = new Partner("John Doe", "email@example.com", company);
        when(companyRepository.findById(any(UUID.class))).thenReturn(Optional.of(company));
        when(partnerRepository.save(any(Partner.class))).thenReturn(partner);
        when(partnerRepository.findById(any(UUID.class))).thenReturn(Optional.of(partner));
        when(partnerRepository.findAll()).thenReturn(Collections.singletonList(partner));
    }

    @Test
    public void testCreatePartner() throws Exception {
        UUID companyId = UUID.randomUUID();
        CompanyDto companyDto = new CompanyDto("Test Company", "123123123123");
        PartnerDto partnerDto = new PartnerDto("John Doe", "email@example.com", companyDto);
        when(partnerService.createPartner(any(PartnerDto.class), any(UUID.class))).thenReturn(partnerDto);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/partners/{companyId}", companyId)
                .content("{\"name\":\"John Doe\",\"taxId\":\"email@example.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(partnerDto.getName()));
    }

    @Test
    public void testGetPartner() throws Exception {
        UUID partnerId = UUID.randomUUID();
        CompanyDto companyDto = new CompanyDto("Test Company", "123123123123");
        PartnerDto partnerDto = new PartnerDto(partnerId, "John Doe",
                "email@example.com", companyDto);

        when(partnerService.getPartnerById(partnerId)).thenReturn(partnerDto);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/partners/{partnerId}", partnerId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(partnerDto.getName()));
    }

    @Test
    public void testGetAllPartners() throws Exception {
        UUID partnerId = UUID.randomUUID();
        CompanyDto companyDto = new CompanyDto("Test Company", "123123123123");

        PartnerDto partnerDto = new PartnerDto(partnerId, "John Doe",
                "email@example.com", companyDto);

        when(partnerService.getAllPartners()).thenReturn(Collections.singletonList(partnerDto));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/partners")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testUpdatePartner() throws Exception {
        UUID partnerId = UUID.randomUUID();
        CompanyDto companyDto = new CompanyDto("Test Company", "123123123123");

        PartnerDto partnerDto = new PartnerDto(partnerId, "Jane Doe", "09876543210",
                companyDto);

        when(partnerService.updatePartner(any(UUID.class),
                any(PartnerDto.class))).thenReturn(partnerDto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/partners/{partnerId}",
                partnerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jane Doe\",\"taxId\":\"09876543210\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    public void testDeletePartner() throws Exception {
        UUID partnerId = UUID.randomUUID();
        doNothing().when(partnerService).deletePartner(partnerId);

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/partners/{partnerId}",
                partnerId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

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

import com.example.company_service.controller.CompanyController;
import com.example.company_service.dto.CompanyDto;
import com.example.company_service.entity.Company;
import com.example.company_service.mapper.CompanyMapper;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.repository.PartnerRepository;
import com.example.company_service.service.CompanyService;

@WebMvcTest(CompanyController.class)
public class CompanyControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private PartnerRepository partnerRepository;

    @Spy
    private CompanyService companyService;

    @Mock
    private CompanyMapper companyMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Company company = new Company("Test Company", "123123123123");
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyRepository.findById(any(UUID.class))).thenReturn(Optional.of(company));
        when(companyRepository.findAll()).thenReturn(Collections.singletonList(company));
    }

    @Test
    public void testCreateCompany() throws Exception {
        CompanyDto companyDto = new CompanyDto("Test Company", "123123123123");
        when(companyService.createCompany(any(CompanyDto.class))).thenReturn(companyDto);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/companies")
                .content("{\"name\":\"Test Company\",\"cnpj\":\"123123123123\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(companyDto.getName()));
    }

    @Test
    public void testGetCompany() throws Exception {
        UUID companyId = UUID.randomUUID();
        CompanyDto companyDto = new CompanyDto(companyId, "Test Company",
                "123123123123");

        when(companyService.getCompany(companyId)).thenReturn(companyDto);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/companies/{companyId}", companyId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(companyDto.getName()));
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        UUID companyId = UUID.randomUUID();
        CompanyDto companyDto = new CompanyDto(companyId, "Test Company", "123123123123");

        when(companyService.getAllCompanies()).thenReturn(Collections.singletonList(companyDto));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/companies")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Company"));
    }

    @Test
    public void testUpdateCompany() throws Exception {
        UUID companyId = UUID.randomUUID();
        CompanyDto companyDto = new CompanyDto(companyId, "Test Companyx",
                "123123123123");

        when(companyService.updateCompany(companyId, companyDto)).thenReturn(companyDto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/companies/{companyId}", companyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Company\",\"cnpj\":\"123123123123\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Company"));
    }

    @Test
    public void testDeleteCompany() throws Exception {
        UUID companyId = UUID.randomUUID();
        doNothing().when(companyService).deleteCompany(companyId);

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/companies/{companyId}", companyId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

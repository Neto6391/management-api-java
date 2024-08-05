
package com.example.company_service.unit.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.company_service.entity.Company;

import com.example.company_service.repository.CompanyRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("Should Return Correct Company When Given Valid Id")
    public void shouldReturnCorrectCompanyWhenGivenValidId() {
        Company company = new Company("Test Company", "123123123213");
        company = companyRepository.save(company);

        Optional<Company> found = companyRepository.findById(company.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(company.getName());
    }

    @Test
    @DisplayName("Should Save a Company and return Saved Entity")
    public void shouldSaveCompanyAndReturnSavedEntity() {
        Company company = new Company("Test Company", "123123123213");

        Company savedCompany = companyRepository.save(company);

        assertThat(savedCompany.getId()).isNotNull();
        assertThat(savedCompany.getName()).isEqualTo(company.getName());
    }
}

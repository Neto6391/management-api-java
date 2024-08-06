package com.example.company_service.unit.repository;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.company_service.entity.Company;
import com.example.company_service.entity.Partner;
import com.example.company_service.repository.CompanyRepository;
import com.example.company_service.repository.PartnerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PartnerRepositoryTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    @DisplayName("Should Return Correct Partner When Given Valid Id")
    public void shouldReturnCorrectPartnerWhenGivenValidId() {
        Company company = new Company("Test Company", "123123123213");
        company = companyRepository.save(company);

        Partner partner = new Partner("Test Partner", "emailTest@example.com",
                company);
        partner = partnerRepository.save(partner);

        Optional<Partner> found = partnerRepository.findById(partner.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(partner.getName());
    }

    @Test
    @DisplayName("Should Save a Partner and return Saved Entity")
    public void shouldSavePartnerAndReturnSavedEntity() {
        Company company = new Company("Test Company", "123123123213");
        Company savedCompaby = companyRepository.save(company);

        Partner partner = new Partner("Test Partner", "emailTest@example.com",
                savedCompaby);
        Partner savedPartner = partnerRepository.save(partner);

        assertThat(savedPartner.getId()).isNotNull();
        assertThat(savedPartner.getName()).isEqualTo(partner.getName());
    }

}
